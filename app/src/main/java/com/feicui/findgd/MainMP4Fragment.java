package com.feicui.findgd;

import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import java.io.FileDescriptor;
import java.io.IOException;

/**
 * Created by LINS on 2016/12/29.
 * Please Try Hard
 */
public class MainMP4Fragment extends Fragment implements TextureView.SurfaceTextureListener{
    TextureView textureView;
    MediaPlayer mediaPlayer;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        textureView = new TextureView(getContext());

        return textureView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textureView.setSurfaceTextureListener(this);
    }

    @Override
    public void onSurfaceTextureAvailable(final SurfaceTexture surface, int width, int height) {

        try {
            //打开资源文件
            AssetFileDescriptor openFd = getContext().getAssets().openFd("welcome.mp4");
            FileDescriptor fileDescriptor = openFd.getFileDescriptor();
            mediaPlayer = new MediaPlayer();
            //设置播放的资源给MediaPlayer
            mediaPlayer.setDataSource(fileDescriptor,openFd.getStartOffset(),openFd.getLength());
            mediaPlayer.prepareAsync();//异步
            //设置监听：看有没有准备好，有，就不放
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                //准备好了，开始播放
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Surface mySurface = new Surface(surface);
                    mediaPlayer.setSurface(mySurface);
                    mediaPlayer.setLooping(true);
                    mediaPlayer.seekTo(0);
                    mediaPlayer.start();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer!=null){

        }
    }
}
