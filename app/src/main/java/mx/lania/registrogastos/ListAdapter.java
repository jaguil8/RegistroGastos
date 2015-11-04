package mx.lania.registrogastos;

import java.util.ArrayList;

import android.app.LauncherActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.ImageButton;
import android.widget.TextView;


public class ListAdapter extends ArrayAdapter<String>  {
    customButtonListener customListner;

    public interface customButtonListener {
        public void onButtonClickListner(int position,String value);
    }

    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }

    private Context context;
    private ArrayList<String> data = new ArrayList<String>();

    public ListAdapter(Context context, ArrayList<String> dataItem) {
        super(context, R.layout.child_listview, dataItem);
        this.data = dataItem;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
                //View row = super.getView(position, convertView, parent);
            //if (row == null) {
                //LayoutInflater grow= LayoutInflater.from(getBaseContext());
                LayoutInflater inflater = LayoutInflater.from(context);
                /*row = inflater.inflate(R.layout.child_listview, null);
                row.setLongClickable(true);
                row.setClickable(true);*/

                //LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(R.layout.child_listview, null);
                viewHolder = new ViewHolder();
                viewHolder.text = (TextView) convertView
                        .findViewById(R.id.childTextView);
                viewHolder.button1 = (ImageButton) convertView
                        .findViewById(R.id.childButton1);
                viewHolder.button2 = (ImageButton) convertView
                        .findViewById(R.id.childButton2);
                viewHolder.button3 = (ImageButton) convertView
                        .findViewById(R.id.childButton3);
                convertView.setTag(viewHolder);
            //}
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final String temp = getItem(position);
        viewHolder.text.setText(temp);
        viewHolder.button1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (customListner != null) {
                    customListner.onButtonClickListner(position, "1");
                }

            }
        });
        viewHolder.button2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (customListner != null) {
                    customListner.onButtonClickListner(position, "2");
                }

            }
        });
        viewHolder.button3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (customListner != null) {
                    customListner.onButtonClickListner(position, "3");
                }

            }
        });

        return convertView;
    }

    public class ViewHolder {
        TextView text;
        ImageButton button1;
        ImageButton button2;
        ImageButton button3;
    }
}
