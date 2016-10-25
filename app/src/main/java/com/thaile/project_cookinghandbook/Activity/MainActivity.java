package com.thaile.project_cookinghandbook.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thaile.project_cookinghandbook.Adapter.MyFragPagerAdapter;
import com.thaile.project_cookinghandbook.Adapter.SearchAdapter;
import com.thaile.project_cookinghandbook.Adapter.TopicAdapter;
import com.thaile.project_cookinghandbook.AppHelper;
import com.thaile.project_cookinghandbook.DBManager.FirebaseManager;
import com.thaile.project_cookinghandbook.Fragment.FragmentTopic.FragmentDoUong;
import com.thaile.project_cookinghandbook.Fragment.FragmentTopic.FragmentMeVaBe;
import com.thaile.project_cookinghandbook.Fragment.FragmentTopic.FragmentMonAnVat;
import com.thaile.project_cookinghandbook.Fragment.FragmentTopic.FragmentMonBanh;
import com.thaile.project_cookinghandbook.Fragment.FragmentTopic.FragmentMonChay;
import com.thaile.project_cookinghandbook.Fragment.FragmentTopic.FragmentMonDiemTam;
import com.thaile.project_cookinghandbook.Fragment.FragmentTopic.FragmentMonHangNgay;
import com.thaile.project_cookinghandbook.Fragment.FragmentTopic.FragmentMonTruyenThong;
import com.thaile.project_cookinghandbook.Object.ItemFood;
import com.thaile.project_cookinghandbook.Object.ItemTopicBar;
import com.thaile.project_cookinghandbook.R;
import com.thaile.project_cookinghandbook.ZoomOutPageTransformer;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TopicAdapter.MoveFragment, FirebaseManager.CallChangeData, GoogleApiClient.OnConnectionFailedListener {
    private static final int REQUEST_PERMISSION = 1010;
    private TextView txtv_user_name;
    private ImageView iv_menu;
    private ImageView img_delete_text;
    private DrawerLayout drawerLayout;
    private TabLayout tabLayout;
    private ArrayList<ItemTopicBar> arrayList = new ArrayList<>();
    private ViewPager viewPager;
    private MyFragPagerAdapter myFragPagerAdapter;
    private RecyclerView recyclerViewBar;
    private AutoCompleteTextView search_view;
    private SearchAdapter searchAdapter;
    private DatabaseReference mData;

    private LinearLayout signInButton;
    private LinearLayout logOutButton;
    private static int RC_SIGN_IN = 109;
    private static String TAG = "MAIN_ACTIVITY";
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private CircleImageView img_user_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MultiDex.install(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        requestAppPermissions(new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_PERMISSION);

        boolean isConnect = AppHelper.isNetworkAvailable(MainActivity.this);
        if (!isConnect){
            Toast.makeText(MainActivity.this, "Không có kết nối. Hãy thử lại sau!", Toast.LENGTH_SHORT).show();
            drawerLayout.openDrawer(Gravity.LEFT);
        }

        initView();
        initGoogleAccount();


    }

    private void initView() {
        initList(); 
        //
        iv_menu = (ImageView)findViewById(R.id.iv_menu);
        iv_menu.setOnClickListener(this);
        //
        img_delete_text = (ImageView)findViewById(R.id.img_delete_text);
        img_delete_text.setOnClickListener(this);
        //
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager)findViewById(R.id.pager);
        recyclerViewBar = (RecyclerView)findViewById(R.id.recycle_view_bar);
        TopicAdapter tp = new TopicAdapter(MainActivity.this,arrayList);
        recyclerViewBar.setAdapter(tp);
        tp.setMoveFragment(MainActivity.this);

        myFragPagerAdapter = new MyFragPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myFragPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
        myFragPagerAdapter.setTabLayout(tabLayout);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());

        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewBar.setLayoutManager(layoutManager);
        (new FirebaseManager()).setChange(MainActivity.this);
        initSearchView();

        //
        img_user_photo = (CircleImageView) findViewById(R.id.img_user_photo);
        txtv_user_name = (TextView) findViewById(R.id.txtv_user_name);
        signInButton = (LinearLayout) findViewById(R.id.google_login);
        signInButton.setOnClickListener(this);
        logOutButton = (LinearLayout) findViewById(R.id.google_logout);
        logOutButton.setOnClickListener(this);
    }

    private void initGoogleAccount() {
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null) {
                    Uri uri = user.getPhotoUrl();
                    String ussname = user.getDisplayName();
                    txtv_user_name.setText(ussname);
                    Glide.with(MainActivity.this).load(uri).into(img_user_photo);
                    txtv_user_name.setVisibility(View.VISIBLE);
                    img_user_photo.setVisibility(View.VISIBLE);
                    signInButton.setVisibility(View.INVISIBLE);
                    logOutButton.setVisibility(View.VISIBLE);
                }
                else {
                    txtv_user_name.setVisibility(View.INVISIBLE);
                    img_user_photo.setVisibility(View.INVISIBLE);
                    signInButton.setVisibility(View.VISIBLE);
                    logOutButton.setVisibility(View.INVISIBLE);
                }
            }
        };

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void initSearchView() {
        search_view = (AutoCompleteTextView)findViewById(R.id.search_view);
        search_view.setOnClickListener(MainActivity.this);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Fragment fragment = myFragPagerAdapter.getItem(position);
                String name = null;
                if (fragment instanceof FragmentMonHangNgay) {
                    name = ((FragmentMonHangNgay) fragment).ROOT_NAME;
                    queryData(name);
                } else if(fragment instanceof FragmentMonAnVat){
                    name = ((FragmentMonAnVat) fragment).ROOT_NAME;
                    queryData(name);
                }else if(fragment instanceof FragmentDoUong){
                    name = ((FragmentDoUong) fragment).ROOT_NAME;
                    queryData(name);
                }else if(fragment instanceof FragmentMonDiemTam){
                    name = ((FragmentMonDiemTam) fragment).ROOT_NAME;
                    queryData(name);
                }else if(fragment instanceof FragmentMonBanh){
                    name = ((FragmentMonBanh) fragment).ROOT_NAME;
                    queryData(name);
                }else if(fragment instanceof FragmentMeVaBe){
                    name = ((FragmentMeVaBe) fragment).ROOT_NAME;
                    queryData(name);
                }else if(fragment instanceof FragmentMonChay){
                    name = ((FragmentMonChay) fragment).ROOT_NAME;
                    queryData(name);
                }else if(fragment instanceof FragmentMonTruyenThong){
                    name = ((FragmentMonTruyenThong) fragment).ROOT_NAME;
                    queryData(name);
                }

            }
            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void queryData(String name) {
        mData = FirebaseDatabase.getInstance().getReference();
        final ArrayList<ItemFood> item = new ArrayList<>();
        mData.child(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                item.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ItemFood itemFood = snapshot.getValue(ItemFood.class);
                    item.add(new ItemFood(snapshot.getKey().toString(),itemFood.getImgFood1(),
                            itemFood.getImgFood2(), itemFood.getImgFood3(),
                            itemFood.getProcessFood(), itemFood.getMaterialFood(),
                            itemFood.getTitleFood(), itemFood.getIntroduction(), itemFood.getTimestamp()));
                }
                search_view.setThreshold(1);
                searchAdapter = new SearchAdapter(MainActivity.this, item);
                searchAdapter.notifyDataSetChanged();
                search_view.setAdapter(searchAdapter);
                final ArrayList<ItemFood> finalItem = item;
                search_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ItemFood itemFood = finalItem.get(position);
                        String idFood = itemFood.getId();
                        String title = itemFood.getTitleFood();
                        String imgF1 = itemFood.getImgFood1();
                        String imgF2 = itemFood.getImgFood2();
                        String imgF3 = itemFood.getImgFood3();
                        String process = itemFood.getProcessFood();
                        String material = itemFood.getMaterialFood();
                        String introduction = itemFood.getIntroduction();
                        AppHelper.moveData(R.id.activity_home, getSupportFragmentManager(), idFood,title, imgF1, imgF2, imgF3, process, material, introduction);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_menu:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.search_view:
                img_delete_text.setVisibility(View.VISIBLE);
                break;
            case R.id.img_delete_text:
                search_view.setText("");
                break;
            case R.id.google_login:
                signIn();
                break;
            case R.id.google_logout:
                signOut();
                logOutButton.setVisibility(View.INVISIBLE);
                signInButton.setVisibility(View.VISIBLE);
                txtv_user_name.setText("");
                img_user_photo.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null)
            mAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
                Toast.makeText(this, "Xin chào "+account.getDisplayName(), Toast.LENGTH_SHORT).show();
            }
            else
            Toast.makeText(this, "Hãy kiểm tra kết nối và thử lại", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Google Login Failed");

        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct){
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        txtv_user_name.setText(acct.getDisplayName());
                        Glide.with(MainActivity.this).load(acct.getPhotoUrl()).into(img_user_photo);
                        img_user_photo.setVisibility(View.VISIBLE);
                        txtv_user_name.setVisibility(View.VISIBLE);
                        signInButton.setVisibility(View.INVISIBLE);
                        logOutButton.setVisibility(View.VISIBLE);
                    }
                });

    }

    private void signIn(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut(){
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                Toast.makeText(MainActivity.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
            }
        });
       // FirebaseAuth.getInstance().signOut();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "Connection failed.");
    }

    public void initList(){
        arrayList.clear();
        arrayList.add(new ItemTopicBar("ic_home.png", "Trang chủ"));
        arrayList.add(new ItemTopicBar("ic_favorite.png", "Danh sách yêu thích"));
        arrayList.add(new ItemTopicBar("ic_photo.png", "Thư viện ảnh"));
        arrayList.add(new ItemTopicBar("ic_social.png", "Khám phá"));
        arrayList.add(new ItemTopicBar("ic_infor.png", "Thông tin ứng dụng"));
    }

    @Override
    public void moveToFragment(int pos) {
        if (pos == 0) {
            drawerLayout.closeDrawers();
        } else if (pos == 1){
            showFavorite();
        } else if (pos == 2){
            showGallery();
        } else if (pos == 3){
            showSocial();
        }
    }

    private void showSocial() {
        Intent intent = new Intent(MainActivity.this, SocialNetWorkActivity.class);
        startActivity(intent);
    }

    @Override
    public void changeData() {
        myFragPagerAdapter.notifyDataSetChanged();
    }

    public void onPermissionsGranted(int requestCode) {
        //Do anything when permisson granted
        //Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_LONG).show();
    }

    public void requestAppPermissions(final String[]requestedPermissions, final int requestCode) {

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        boolean showRequestPermissions = false;
        for(String permission: requestedPermissions) {
            permissionCheck = permissionCheck + ContextCompat.checkSelfPermission(this, permission);
            showRequestPermissions = showRequestPermissions || ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
        }

        if (permissionCheck!=PackageManager.PERMISSION_GRANTED) {
            if(showRequestPermissions) {
            } else {
                ActivityCompat.requestPermissions(this, requestedPermissions, requestCode);
            }
        } else {
            onPermissionsGranted(requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int permisson : grantResults) {
            permissionCheck = permissionCheck + permisson;
        }

        if ((grantResults.length > 0) && PackageManager.PERMISSION_GRANTED == permissionCheck) {
            onPermissionsGranted(requestCode);
        } else {

        }
    }

    public void showFavorite(){
        Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
        startActivity(intent);
    }

    public void showGallery(){
        Intent intent = new Intent(MainActivity.this, PictureActivity.class);
        startActivity(intent);
    }
}
