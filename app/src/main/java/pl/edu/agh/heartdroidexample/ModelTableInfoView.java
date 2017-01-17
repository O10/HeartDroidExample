package pl.edu.agh.heartdroidexample;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import heart.HeaRT;
import heart.State;
import heart.StateElement;
import heart.xtt.Attribute;
import heart.xtt.XTTModel;

/**
 * Created by O10 on 17.01.2017.
 */

public class ModelTableInfoView extends TableLayout {

    private Map<String, TableRow> attributeNameToRowMap = new HashMap<>();
    private XTTModel model;

    public ModelTableInfoView(Context context) {
        super(context);
    }

    public ModelTableInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void initForModel(XTTModel model) {

        this.model = model;

        setStretchAllColumns(true);

        for (Attribute attribute : model.getAttributes()) {
            TableRow tableRow = new TableRow(getContext());

            tableRow.addView(new TextView(getContext()));
            tableRow.addView(new TextView(getContext()));
            tableRow.addView(new TextView(getContext()));

            attributeNameToRowMap.put(attribute.getName(), tableRow);
            addView(tableRow);
        }

        notifyModelChanged();
    }

    public void notifyModelChanged() {
        final State currentState = HeaRT.getWm().getCurrentState(model);

        for (StateElement se : currentState) {
            TableRow attRow = attributeNameToRowMap.get(se.getAttributeName());

            updateRowText(attRow, 0, se.getAttributeName());
            updateRowText(attRow, 1, se.getValue().toString());
            updateRowText(attRow, 2, String.valueOf(se.getValue().getCertaintyFactor()));
        }

    }


    private void updateRowText(TableRow attRow, int column, String value) {
        TextView attNameText = (TextView) attRow.getChildAt(column);
        attNameText.setText(value);
    }
}
