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
// 这个里面我们主要是进行视频的播放
public class MainMP4Fragment extends Fragment implements TextureView.SurfaceTextureListener{
    TextureView textureView;
    MediaPlayer mediaPlayer;

    /**
     * 1. 使用MediaPlayer来进行播放视频
     * 2. 展示视频播放：SurfaceView,我们在这里使用的是TextureView
     * 3. 使用TextureView：需要使用的是SurfaceTexture：使用这个来渲染、呈现播放的内容
     */

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        textureView = new TextureView(getContext());

        return textureView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 设置监听：因为播放显示内容需要SurfaceTexture，所以设置一个监听，看SurfaceTexture有没有准备好或有没有变化等
        textureView.setSurfaceTextureListener(this);
    }

    @Override
    public void onSurfaceTextureAvailable(final SurfaceTexture surface, int width, int height) {
            /**
             * 1. 播放什么呢？找到我们播放的资源
             * 2. 可以播放了：MediaPlayer来进行播放
             *      创建、设置播放的资源、设置播放的同异步等。。
             *      MediaPlayer有没有准备好：好了，就直接开始播放吧
             * 3. 页面销毁了：MediaPlayer资源释放掉。。。
             */
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
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
