package com.thaile.project_cookinghandbook.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.thaile.project_cookinghandbook.Object.ItemSocial;
import com.thaile.project_cookinghandbook.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Thai Le on 10/13/2016.
 */

public class DialogGallery extends Dialog implements View.OnClickListener {
    private Context context;
    private File file;
    private String path;
    private StorageReference storageRef;
    private String username, status;
    private String userphoto;
    private RelativeLayout layout_main;
    private FrameLayout frameLayout;
    private CircleImageView img_user;
    private TextView txtv_user;
    private EditText editText_status;
    private ImageView img;
    private ProgressBar progressBar;
    private byte[] data;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private Button btnCancel, btnOk;

    public DialogGallery(Context context, String path, byte[] data) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_gallery);
        this.context = context;
        this.path = path;
        this.data = data;
        initView();
    }

    private void initView() {
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(this);
        btnOk = (Button) findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(this);
        layout_main = (RelativeLayout)findViewById(R.id.layout_main);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        frameLayout = (FrameLayout) findViewById(R.id.frame_main);

        txtv_user = (TextView) findViewById(R.id.txtv_user);
        img_user = (CircleImageView) findViewById(R.id.img_user);
        editText_status = (EditText)findViewById(R.id.edt_status);

        img = (ImageView) findViewById(R.id.img_demo);
        file = new File(path);

        //
        Uri uri = Uri.fromFile(file);
        Glide.with(context).load(uri).into(img);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://cookinghandbookproject.appspot.com");

        //
        username = user.getDisplayName();
        userphoto = user.getPhotoUrl().toString();
        status = editText_status.getText().toString();

        txtv_user.setText(username);
        Glide.with(context).load(userphoto).into(img_user);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_ok:
                firebaseImageUpload();
                break;

            case R.id.btn_cancel:
                dismiss();
                break;
            default:
                break;
        }
    }

    private void firebaseImageUpload() {
        layout_main.setVisibility(View.INVISIBLE);
        frameLayout.setBackgroundColor(Color.TRANSPARENT);
        progressBar.setVisibility(View.VISIBLE);
        Calendar calendar = Calendar.getInstance();
        StorageReference moutainsRef = storageRef.child("IMAGE_"+calendar.getTimeInMillis()+".jpg");

        try {
            UploadTask uploadTask = moutainsRef.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(context, "Lỗi", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    saveToStorage(downloadUrl.toString());
                    Toast.makeText(context, "Tải ảnh lên thành công!", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void saveToStorage(String link) {
        DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
        String timeStamp = "-" + new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
        status = editText_status.getText().toString();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = df.format(c.getTime());
        ItemSocial itemSocial = new ItemSocial(link, username, userphoto, status, Long.parseLong(timeStamp), dateTime);
        mData.child("Photos").push().setValue(itemSocial);
    }



}
