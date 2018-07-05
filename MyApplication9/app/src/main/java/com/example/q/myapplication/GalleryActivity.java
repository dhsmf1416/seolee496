package com.example.q.myapplication;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Button;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.GridView;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import android.graphics.Color;
import android.widget.ToggleButton;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.DialogInterface;
import android.animation.ObjectAnimator;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.graphics.Rect;
import android.graphics.Point;
import android.content.res.ColorStateList;
import android.support.design.widget.FloatingActionButton;

import static android.app.Activity.RESULT_CANCELED;

public class GalleryActivity extends Fragment {
    DatabaseHelper databaseHelper;
    ArrayList<byte[]> images;
    GridView gridData;
    ArrayList<Integer> iidList;
    ArrayList<Integer> deleteList;
    ArrayList<View> viewList;
    Boolean deleteFlag;
    FloatingActionButton toggle;
    FloatingActionButton btnCamera;
    Animator anim;
    Animation animSlide;
    Animation animSlide2;
    Rect startBounds;
    Rect finalBounds;
    Point globalOffset;
    View thumbView;
    int animDuration;
    int currentID;
    float startScaleFinal;
    private TouchEventView expandedImageView;
    private ImageView clonedImageView;
    private FrameLayout alltouch;
    ViewGroup.LayoutParams defaultParams;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gallery_main,container,false);
        return rootView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        expandedImageView = (TouchEventView)getView().findViewById(R.id.expanded_image);
        defaultParams = expandedImageView.getLayoutParams();
        deleteFlag = false;
        deleteList = new ArrayList<>();
        viewList = new ArrayList<>();
        super.onCreate(savedInstanceState);
        gridData= (GridView) getView().findViewById(R.id.gv_emp);
        alltouch=(FrameLayout) getView().findViewById(R.id.alltouch);
        clonedImageView = (ImageView)getView().findViewById(R.id.anim_image);
        animDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
        gridData.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                return false;
            }
        });
        gridData.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(!deleteFlag)
                {
                    // i <= position
                    zoomImageFromThumb(view, images.get(i));
                    currentID = i;
                    return;
                }
                ImageView x = view.findViewById(R.id.imageView1);
                if(findId(iidList.get(i))) {
                    x.setImageBitmap(addNotCheckBitmap(getImage(images.get(i))));
                }
                else
                {
                    x.setImageBitmap(addCheckBitmap(getImage(images.get(i))));
                    deleteList.add(iidList.get(i));
                    viewList.add(view);
                }
            }
        });
        btnCamera = (FloatingActionButton) getView().findViewById(R.id.fab2);
        databaseHelper = new DatabaseHelper(getActivity());
        ResetAndShow();
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(!deleteFlag) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 0);
                }
                else
                {
                    for(int i=0;i<deleteList.size();i++) {
                        databaseHelper.deleteImage(deleteList.get(i));
                    }
                    ResetAndShow();
                    deleteFlag = false;
                    for(int i=0;i<images.size();i++) {
                        ImageView x = getViewByPosition(i, gridData).findViewById(R.id.imageView1);
                        x.setImageBitmap(getImage(images.get(i)));
                    }
                    deleteList = new ArrayList<>();
                    viewList = new ArrayList<>();
                    toggle.setImageResource(android.R.drawable.ic_menu_delete);
                    btnCamera.setImageResource(android.R.drawable.ic_menu_camera);
                }
            }
        });
        toggle = (FloatingActionButton) getView().findViewById(R.id.fab1);
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!deleteFlag)
                {
                    if(alltouch.getVisibility() == View.VISIBLE)
                    {
                        showMessage(getContext());
                        return;
                    }
                    deleteFlag = true;
                    for(int i=0;i<images.size();i++) {
                        ImageView x = getViewByPosition(i, gridData).findViewById(R.id.imageView1);
                        x.setImageBitmap(addNotCheckBitmap(getImage(images.get(i))));
                    }
                    toggle.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
                    btnCamera.setImageResource(android.R.drawable.ic_menu_delete);
                }
                else
                {
                    deleteFlag = false;
                    for(int i=0;i<images.size();i++) {
                        ImageView x = getViewByPosition(i, gridData).findViewById(R.id.imageView1);
                        x.setImageBitmap(getImage(images.get(i)));
                    }
                    deleteList = new ArrayList<>();
                    viewList = new ArrayList<>();
                    toggle.setImageResource(android.R.drawable.ic_menu_delete);
                    btnCamera.setImageResource(android.R.drawable.ic_menu_camera);
                }
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED) {
            ResetAndShow();
            return;
        }
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        Bitmap newBitmap = new ImageAdapter(getActivity(),R.layout.row,new Bitmap[1]).getOurBitmap(bitmap);
        long x = databaseHelper.createImage(getBytes(newBitmap));
        ResetAndShow();

    }
    public Bitmap addCheckBitmap(Bitmap bitmap)
    {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap result = Bitmap.createBitmap(w,h,bitmap.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(bitmap,0,0,null);
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),android.R.drawable.presence_online),3,3,null);
        return result;
    }
    public Bitmap addNotCheckBitmap(Bitmap bitmap)
    {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap result = Bitmap.createBitmap(w,h,bitmap.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(bitmap,0,0,null);
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),android.R.drawable.presence_invisible),3,3,null);
        return result;
    }
    public static byte[] getBytes(Bitmap bitmap)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,0,stream);
        return stream.toByteArray();
    }
    public static Bitmap getImage(byte[] image)
    {
        return BitmapFactory.decodeByteArray(image,0,image.length);
    }
    public void showList()
    {
        Bitmap[] bitmaps = new Bitmap[images.size()];
        for(int i=0;i<images.size();i++)
        {
            bitmaps[i] = getImage(images.get(i));
        }
        gridData.setAdapter(new ImageAdapter(getActivity(), R.layout.row,bitmaps));
    }
    public Boolean findId(int x)
    {
        for(int i=0;i<deleteList.size();i++)
            if(deleteList.get(i) == x)
            {
                deleteList.remove(i);
                viewList.remove(i);
                return true;
            }
        return false;
    }
    public void ResetAndShow()
    {
        images = databaseHelper.getAllImage();
        iidList = databaseHelper.getAllId();
        showList();
    }
    private void zoomImageFromThumb(final View thumbView, byte[] image)
    {
        if(anim != null)
        {
            anim.cancel();
        }
        expandedImageView = (TouchEventView)getView().findViewById(R.id.expanded_image);
        expandedImageView.setLayoutParams(defaultParams);
        expandedImageView.upper = this;
        this.thumbView = thumbView;
        expandedImageView.setImageBitmap(getImage(image));
        startBounds = new Rect();
        finalBounds = new Rect();
        globalOffset = new Point();

        thumbView.getGlobalVisibleRect(startBounds);
        getView().findViewById(R.id.container).getGlobalVisibleRect(finalBounds,globalOffset);

        startBounds.offset(-globalOffset.x,-globalOffset.y);
        finalBounds.offset(-globalOffset.x,-globalOffset.y);

        float startScale;

        if((float) finalBounds.width() / finalBounds.height() > (float) startBounds.width() / startBounds.height())
        {
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        }
        else
        {
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        AnimatorSet set = new AnimatorSet();
        set.play(ObjectAnimator.ofFloat(expandedImageView,View.X,startBounds.left,finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView,View.Y,startBounds.top,finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView,View.SCALE_X,startScale,1f))
                .with(ObjectAnimator.ofFloat(expandedImageView,View.SCALE_Y,startScale,1f));
        set.setDuration(animDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation)
            {
                anim = null;
            }

            @Override
            public void onAnimationCancel(Animator animation)
            {
                anim = null;
            }

        });
        set.start();
        anim = set;

        startScaleFinal = startScale;
        alltouch.setVisibility(View.VISIBLE);
        btnCamera.setVisibility(View.GONE);




    }
    public boolean swipeok()
    {
        if(expandedImageView == null)
            return true;
        return expandedImageView.getVisibility() != View.VISIBLE;
    }
    public void closeWindow()
    {
        if (anim != null) {
            anim.cancel();
        }
        AnimatorSet set = new AnimatorSet();
        clonedImageView.setVisibility(View.GONE);
        alltouch.setVisibility(View.GONE);
        set.play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScaleFinal))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScaleFinal));

        set.setDuration(animDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                thumbView.setAlpha(1f);
                alltouch.setVisibility(View.GONE);
                expandedImageView.setVisibility(View.GONE);
                expandedImageView.setLayoutParams(new ConstraintLayout.LayoutParams(0,0));
                btnCamera.setVisibility(View.VISIBLE);
                btnCamera = getView().findViewById(R.id.fab2);
                anim = null;
                System.out.println("!");
                ResetAndShow();
            }
            @Override
            public void onAnimationCancel(Animator animation) {
                thumbView.setAlpha(1f);
                expandedImageView.setVisibility(View.GONE);
                alltouch.setVisibility(View.GONE);
                btnCamera.setVisibility(View.VISIBLE);
                btnCamera = getView().findViewById(R.id.fab2);
                anim = null;
                ResetAndShow();
            }


        });
        anim = set;
        set.start();
    }
    private void showMessage(Context mContext)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(true);
        builder.setTitle("정말로");
        builder.setMessage("지워도 됩니까?");
        builder.setPositiveButton("네",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        databaseHelper.deleteImage(iidList.get(currentID));
                        expandedImageView.setVisibility(View.GONE);
                        alltouch.setVisibility(View.GONE);
                        btnCamera.setVisibility(View.VISIBLE);
                        toggle.setImageResource(android.R.drawable.ic_menu_delete);
                        btnCamera.setImageResource(android.R.drawable.ic_menu_camera);
                        deleteFlag = false;
                        ResetAndShow();
                    }
    });
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void changeExpanded(boolean isLeft)
    {

        getViewByPosition(currentID,gridData).setAlpha(1f);
        if(isLeft)
        {
           currentID++;
            if(currentID==images.size())
                currentID = 0;
            expandedImageView.setImageBitmap(getImage(images.get(currentID)));

        }else
        {
            currentID--;
            if(currentID<0)
                currentID = images.size()-1;
            expandedImageView.setImageBitmap(getImage(images.get(currentID)));
        }
        float startScale;
        getViewByPosition(currentID,gridData).getGlobalVisibleRect(startBounds);
        startBounds.offset(-globalOffset.x,-globalOffset.y);
        if((float) finalBounds.width() / finalBounds.height() > (float) startBounds.width() / startBounds.height())
        {
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth*5;
        }
        else
        {
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }
        startScaleFinal = startScale;
        getViewByPosition(currentID,gridData).setAlpha(0f);
    }
    public View getViewByPosition(int pos, GridView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

}

