package com.example.the_health_compass;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.menu.MenuAdapter;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class Recycler_Diagnos extends RecyclerView.Adapter<Recycler_Diagnos.ExampleViewHolder> {
    private ArrayList<ListDiagnos> Diagnos;
    private Recycler_Diagnos.ItemClickListener mItemListener;
    String Type = "";
    View v;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView[] textViews = new TextView[8];
        Spinner[] spinners = new Spinner[2];
        CardView cardView;
        EditText editText ;
        Button Send ;
        String Type ;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.CardView);
            textViews[0] = itemView.findViewById(R.id.namesick);
            textViews[1] = itemView.findViewById(R.id.agesick);
            textViews[2] = itemView.findViewById(R.id.partofbody);
            textViews[3] = itemView.findViewById(R.id.PartStyle);
            textViews[4] = itemView.findViewById(R.id.Description_Sick);
            textViews[5] = itemView.findViewById(R.id.Diagnos_System);
            textViews[6] = itemView.findViewById(R.id.Create_Diagnos);
            textViews[7] = itemView.findViewById(R.id.Finish_Update);
            spinners[0] = itemView.findViewById(R.id.Synd);
            spinners[1] = itemView.findViewById(R.id.Ill);
            editText = itemView.findViewById(R.id.Description_Recylcer);
            Send = itemView.findViewById(R.id.Send_Recycler);
            Send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar c = Calendar.getInstance();
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH);
                    int day = c.get(Calendar.DAY_OF_MONTH);
                    int hour = c.get(Calendar.HOUR_OF_DAY);
                    int minute = c.get(Calendar.MINUTE);
                    String Date = String.valueOf(year)+"/"+String.valueOf(month)+"/"+String.valueOf(day)+" "+String.valueOf(hour)+":"+String.valueOf(minute);
                    String[] data = {editText.getText().toString(),Date,String.valueOf(textViews[0].getImeOptions()),String.valueOf(textViews[5].getImeOptions()),textViews[6].getText().toString(), Type};
                    boolean check = new DataAccessLayer().UpdateDiagnos_S_D(data);
                    if (check){
                        androidx.appcompat.app.AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getContext());
                        builder1.setTitle("تم الامر بنجاح");
                        builder1.setMessage("سيتم اعلام الجهة المرسلة للأستشارة");

                        builder1.create();

                        // create the alert dialog with the
                        // alert dialog builder instance
                        builder1.show();
                        cardView.setVisibility(View.INVISIBLE);

                        Data myData = new Data.Builder()
                                .putString("Tile","أستشارة طبية" )
                                .putString("Body", "لديك استشارة طبية قيد الأستجابة يمكنك الرد عليها بالضغط على الأشعار")
                                .putString("User", Type)
                                .build();
                        Constraints constraints = new Constraints.Builder()
                                .setRequiresDeviceIdle(true)
                                .setRequiredNetworkType(NetworkType.CONNECTED)
                                .build();

                        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(Notifications.TimerWithWorkManager.class)
                                .setInputData(myData)
                                .setConstraints(constraints)
                                .addTag("MY_WORK_MANAGER_TAG_ONE_TIME")
                                .build();

                        WorkManager.getInstance().enqueue(oneTimeWorkRequest);
                    }
                }
            });


        }
    }

    public Recycler_Diagnos(String Type,ArrayList<ListDiagnos> exampleList, Recycler_Diagnos.ItemClickListener itemClickListener) {
        Diagnos = exampleList;
        this.Type = Type;
        this.mItemListener = itemClickListener;
    }



    @Override
    public Recycler_Diagnos.ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recycler_diagnos,
                parent, false);
        Recycler_Diagnos.ExampleViewHolder evh = new Recycler_Diagnos.ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(Recycler_Diagnos.ExampleViewHolder holder, int position) {
        ListDiagnos currentItem = Diagnos.get(position);
        try {
            if (currentItem.getDiagnoseSD().getType_Update().equals("Doctor")){
                return;
            }else {
                holder.Type = Type;
                holder.textViews[0].setText(currentItem.getNameSick());
                holder.textViews[0].setImeOptions(Integer.parseInt(currentItem.getDiagnoseSD().getSick_ID()));
                holder.textViews[1].setText(currentItem.getAgeSick());
                holder.textViews[2].setText(currentItem.getPartOfBody());
                holder.textViews[3].setText(currentItem.getStyleBody());
                holder.textViews[4].setText(currentItem.getDescription_Sick());
                holder.textViews[5].setText(currentItem.getDiagnos_System());
                holder.textViews[5].setImeOptions(Integer.parseInt(currentItem.getDiagnoseSD().getTD_ID()));
                holder.textViews[6].setText(currentItem.getCreate_Diagnos());
                holder.textViews[7].setText(currentItem.getFinishe_Update());
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(holder.itemView.getContext(), R.layout.support_simple_spinner_dropdown_item, currentItem.getSyndromeIl());
                holder.spinners[0].setAdapter(arrayAdapter);
                arrayAdapter = new ArrayAdapter<String>(v.getContext(), R.layout.support_simple_spinner_dropdown_item, currentItem.getIllness().values().toArray(new String[currentItem.getIllness().size()]));
                holder.spinners[1].setAdapter(arrayAdapter);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        holder.itemView.setOnClickListener(view -> {
            mItemListener.onItemClick(Diagnos.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return Diagnos.size();
    }

    public interface ItemClickListener{
        void onItemClick(ListDiagnos listDiagnos);
    }

    public void filterList(ArrayList<ListDiagnos> filteredList) {
        Diagnos = filteredList;
        notifyDataSetChanged();
    }
}