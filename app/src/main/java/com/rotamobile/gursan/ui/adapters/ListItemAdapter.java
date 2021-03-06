package com.rotamobile.gursan.ui.adapters;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rotamobile.gursan.R;
import com.rotamobile.gursan.data.Server;
import com.rotamobile.gursan.model.bildirim.BildirimModel;
import com.rotamobile.gursan.model.homeItemClick.ItemClickCheck;
import com.rotamobile.gursan.model.todoList.ListItemAllMessages;
import com.rotamobile.gursan.ui.bottom_navigation.MainBottomNavigation;
import com.rotamobile.gursan.ui.details.Details;
import com.rotamobile.gursan.ui.documents.AddMaterial;
import com.rotamobile.gursan.ui.documents.CaptureImage;
import com.rotamobile.gursan.ui.documents.DisServisForm;
import com.rotamobile.gursan.ui.documents.OpenGalery;
import com.rotamobile.gursan.ui.documents.PickDocument;
import com.rotamobile.gursan.utils.enums.Enums;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import io.paperdb.Paper;
import io.realm.Realm;
import io.realm.RealmResults;

public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ViewHolder> implements Filterable {

    private List<ListItemAllMessages> list_allmesaj;
    private List<ListItemAllMessages> list_allmesajFull;

    ListItemAllMessages listItemAllMessages;
    private Context context;
    private int row_index = -1;
    private ProgressDialog progressDialog;
    private WorkStatusClose workStatusClose = null;
    private Integer get_LoginID = 0;
    private Integer getStatus_id = 0;
    private Realm realm;
    private RealmResults<ItemClickCheck> realmResults;
    private ItemClickCheck itemClickCheck;
    SimpleDateFormat format;

    public ListItemAdapter(List<ListItemAllMessages> list_allmesaj, Context context, Integer getStatus_id) {
        this.list_allmesaj = list_allmesaj;
        list_allmesajFull = new ArrayList<>(list_allmesaj);
        this.context = context;
        this.getStatus_id = getStatus_id;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_allmesaj,viewGroup,false);
        //Progress Diaolog initialize
        progressDialog = new ProgressDialog(context);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

     //Realm initialize
        realm = Realm.getDefaultInstance();

     //get UserID from Login
        String get_userID = Paper.book().read("user_id");
        get_LoginID = Integer.parseInt(get_userID);

        format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S",Locale.getDefault());


        progressDialog.setIndeterminate(true);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        Date date = null;
        String getDate = "";

        listItemAllMessages = list_allmesaj.get(position);

        viewHolder.textViewHead.setText(""+listItemAllMessages.getID());
        viewHolder.textViewDesc.setText(listItemAllMessages.getSubjectText());

        try {
            date = format.parse(listItemAllMessages.getStartDate());
            getDate = date.toString();
            getDate = getDate.substring(0, getDate.indexOf('.') + 20);
            //System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        viewHolder.textTime.setText(getDate);

        Integer i = listItemAllMessages.getWorkOrderServiceID();

      //Check item is opened or not
/*        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                realmResults = realm.where(ItemClickCheck.class).findAll();
                Log.i("Items Count:", "" + realmResults);

                 for (int i = 0; i < realmResults.size(); i++) {
                     Integer item = 0;
                     try {
                         item = Integer.valueOf(realmResults.get(i).getWorkID());
                     }catch (NumberFormatException e){
                         e.printStackTrace();
                     }
                     if(realmResults.get(i).isChecable() == false &&  item == list_allmesaj.get(position).getID()){
                         viewHolder.tag.setVisibility(View.VISIBLE);
                     }else{
                         viewHolder.tag.setVisibility(View.GONE);
                     }
                 }
            }
        });*/

    //Check MoveTypeID is equal or not Enums.kapali
        if(!(listItemAllMessages.getMoveTypeID().equals(Enums.kapali)) && (listItemAllMessages.getAuthorizationUpdate())){

            viewHolder.dot_icon.setVisibility(View.VISIBLE);
        }else{

            viewHolder.dot_icon.setVisibility(View.GONE);
        }

        viewHolder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                row_index = position;
                listItemAllMessages = list_allmesaj.get(position);
                notifyDataSetChanged();
                Intent goDetails = new Intent(context,Details.class);
                goDetails.putExtra("id",listItemAllMessages.getID());// WorkOrder ID
                goDetails.putExtra("proje_name",listItemAllMessages.getProjectName());
                goDetails.putExtra("teritory_name",listItemAllMessages.getTerritoryName());
                goDetails.putExtra("building_name",listItemAllMessages.getBuildingName());
                goDetails.putExtra("area_name",listItemAllMessages.getAreaName());
                goDetails.putExtra("subject_name",listItemAllMessages.getSubjectText());
                goDetails.putExtra("device_name",listItemAllMessages.getDeviceName());
                goDetails.putExtra("start_date",listItemAllMessages.getStartDate());
                goDetails.putExtra("end_date",listItemAllMessages.getEndDate());
                goDetails.putExtra("work_user",listItemAllMessages.getWorkUser());
                goDetails.putExtra("proje_id",listItemAllMessages.getProjectID());
                goDetails.putExtra("teritory_id",listItemAllMessages.getTerritoryID());
                goDetails.putExtra("building_id",listItemAllMessages.getBuildingID());
                goDetails.putExtra("area_id",listItemAllMessages.getAreaID());
                goDetails.putExtra("device_id",listItemAllMessages.getDeviceID());
                goDetails.putExtra("subject_id",listItemAllMessages.getSubjectID());
                goDetails.putExtra("insert_user_id",listItemAllMessages.getInsertUserID());
                goDetails.putExtra("assigned_user_id",listItemAllMessages.getAssignedUserID());
                goDetails.putExtra("auhorizate_update",listItemAllMessages.getAuthorizationUpdate());
                goDetails.putExtra("description_update",listItemAllMessages.getDescription());
                goDetails.putExtra("work_id",listItemAllMessages.getID());//WorkOrder ID
                goDetails.putExtra("status",getStatus_id);//status
                goDetails.putExtra("moveType",listItemAllMessages.getMoveTypeID());//Move TypeID
                goDetails.putExtra("workOrderCategory_id", listItemAllMessages.getWorkOrderCategoryID());
                goDetails.putExtra("workOrderType_id",listItemAllMessages.getWorkOrderTypeID());
                goDetails.putExtra("WorkImportance_id",listItemAllMessages.getWorkImportanceID());
                goDetails.putExtra("WorkOrderService_id",listItemAllMessages.getWorkOrderServiceID());

                context.startActivity(goDetails);

            }
        });


       //PopupMenu Click Listener
        viewHolder.dot_icon.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {

                listItemAllMessages = list_allmesaj.get(position);
              //for Documents and matarial added
                PopupMenu popupMenu = new PopupMenu(context, viewHolder.dot_icon);
            //Checking WorkOrder, iç servis or dış servis or not
                if(listItemAllMessages.getWorkOrderServiceID().equals(Enums.ic_Servis)) {
                    popupMenu.inflate(R.menu.list_item_option_menu);
                }else if(listItemAllMessages.getWorkOrderServiceID().equals(Enums.dis_Servis)){
                    popupMenu.inflate(R.menu.list_item_option_with_disservisform);
                }else{
                    popupMenu.inflate(R.menu.list_item_option_without_material);
                }

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()){

                            case R.id.menu_item_takeCapture:

                                Intent go_capture = new Intent(context,CaptureImage.class);
                                go_capture.putExtra("id",listItemAllMessages.getID());// WorkOrder ID
                                go_capture.putExtra("insert_user_id",listItemAllMessages.getInsertUserID());//Insert User ID
                                context.startActivity(go_capture);
                                break;

                            case R.id.menu_item_add_fromGalery:

                                Intent go_galery = new Intent(context,OpenGalery.class);
                                go_galery.putExtra("id",listItemAllMessages.getID());// WorkOrder ID
                                go_galery.putExtra("insert_user_id",listItemAllMessages.getInsertUserID());//Insert User ID
                                context.startActivity(go_galery);
                                break;

                            case R.id.menu_item_addDocuman:

                                Intent go_document = new Intent(context,PickDocument.class);
                                go_document.putExtra("id",listItemAllMessages.getID());// WorkOrder ID
                                go_document.putExtra("insert_user_id",listItemAllMessages.getInsertUserID());//Insert User ID
                                context.startActivity(go_document);
                                break;

                            case R.id.menu_item_add_material:

                                Intent go_material = new Intent(context,AddMaterial.class);
                                go_material.putExtra("id",listItemAllMessages.getID());// WorkOrder ID
                                go_material.putExtra("insert_user_id",listItemAllMessages.getInsertUserID());//Insert User ID
                                context.startActivity(go_material);
                                break;

                            case R.id.menu_item_disServisFormu:

                                Intent go_disServisFormu = new Intent(context,DisServisForm.class);
                                go_disServisFormu.putExtra("id",listItemAllMessages.getID());// WorkOrder ID
                                go_disServisFormu.putExtra("insert_user_id",listItemAllMessages.getInsertUserID());//Insert User ID
                                context.startActivity(go_disServisFormu);
                                break;

                            case R.id.menu_item_is_kapat:

                                workStatusClose = new WorkStatusClose(listItemAllMessages.getID(),Enums.onay,get_LoginID,listItemAllMessages.getInsertUserID());// WorkOrder ID, Kapalı, User ID , Insert User ID
                                workStatusClose.execute((Void) null);
                                break;


                        }

                        return false;
                    }
                });
                @SuppressLint("RestrictedApi") MenuPopupHelper menuHelper = new MenuPopupHelper(context, (MenuBuilder) popupMenu.getMenu(), viewHolder.dot_icon);
                menuHelper.setForceShowIcon(true);
                menuHelper.show();
                /*popupMenu.show();*/
            }
        });

/*        if(row_index == position){
            viewHolder.linear.setBackgroundColor(Color.parseColor("#6BE9F6"));

        }
        else
        {
            viewHolder.linear.setBackgroundColor(Color.parseColor("#ffffff"));

        }*/
    }

    @Override
    public int getItemCount() {
        return list_allmesaj.size();
    }

  //for SearchView in RecyclerView
    @Override
    public Filter getFilter() {
        return list_allmesaj_filter;
    }

    private Filter list_allmesaj_filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {



            List<ListItemAllMessages> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){

                filteredList.addAll(list_allmesajFull);
            }else{

                String filterPattern = constraint.toString().trim();
//                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ListItemAllMessages item : list_allmesajFull ){

                    if(item != null && item.getID().toString().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            if(results.values != null) {
                list_allmesaj.clear();
                list_allmesaj.addAll((List<ListItemAllMessages>) results.values);
                notifyDataSetChanged();
            }
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder{

        public EditText textViewHead;
        public TextView textViewDesc,textTime,tag;
        public ImageButton dot_icon;
        public LinearLayout linear;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewHead = itemView.findViewById(R.id.txt_head);
            textViewDesc = itemView.findViewById(R.id.txt_desc);
            textTime = itemView.findViewById(R.id.txt_time);
            tag = itemView.findViewById(R.id.txt_tag_bildiirm);
            dot_icon = itemView.findViewById(R.id.txt_option_item);
            linear = itemView.findViewById(R.id.linearLayout);
        }
    }

    public class WorkStatusClose extends AsyncTask<Void, Void, Boolean> {

        private final Integer assigned_id;
        private final Integer workOrder_id;
        private final Integer moveType_id;
        private final Integer insertUser_id;

        private String get_mesaj_workStatusAdd = "";
        String get_mesaj = "";

        WorkStatusClose(Integer workOrder_id,Integer moveType_id,Integer assigned_id,Integer insertUser_id){

            this.workOrder_id = workOrder_id;
            this.moveType_id = moveType_id;
            this.assigned_id = assigned_id;
            this.insertUser_id = insertUser_id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("\tLoading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            progressDialog.setContentView(R.layout.custom_progress);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            try {
                String getWorkStatusAdd_service = Server.WorkStatusAdd(workOrder_id,moveType_id,assigned_id,insertUser_id);
                if(!getWorkStatusAdd_service.trim().equalsIgnoreCase("false")){

                    JSONObject jsonObject = new JSONObject(getWorkStatusAdd_service);
                    get_mesaj = jsonObject.getString("Successful");

                }else{
                    get_mesaj = "false";
                }

            } catch (Exception e) {

            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if(!get_mesaj.equals("false")) {
                progressDialog.dismiss();

                new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("İşlem Mesajı")
                        .setContentText("İşlem Başarılı,İş Kapandı")
                        .setConfirmText("Tamam")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {

                                sweetAlertDialog.dismissWithAnimation();
                                Intent go_home = new Intent(context,MainBottomNavigation.class);
                                context.startActivity(go_home);
                            }
                        })
                        .show();

            }else{

                progressDialog.dismiss();
                Toasty.error(context, "Başarısız", Toast.LENGTH_SHORT, true).show();
            }

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            workStatusClose = null;
            progressDialog.dismiss();
        }
    }
}
