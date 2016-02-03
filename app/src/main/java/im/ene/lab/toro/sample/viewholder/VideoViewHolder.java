/*
 * Copyright 2016 eneim@Eneim Labs, nam@ene.im
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package im.ene.lab.toro.sample.viewholder;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import im.ene.lab.toro.ToroVideoViewHolder;
import im.ene.lab.toro.sample.R;
import im.ene.lab.toro.sample.data.SimpleVideoObject;
import im.ene.lab.toro.sample.util.Util;
import im.ene.lab.toro.widget.ToroVideoView;

/**
 * Created by eneim on 1/30/16.
 */
public class VideoViewHolder extends ToroVideoViewHolder {

  private final String TAG = getClass().getSimpleName();

  public static final int LAYOUT_RES = R.layout.vh_texture_video;

  private ImageView mThumbnail;
  private TextView mInfo;

  public VideoViewHolder(View itemView) {
    super(itemView);
    mThumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
    mInfo = (TextView) itemView.findViewById(R.id.info);
  }

  @Override protected ToroVideoView getVideoView(View itemView) {
    return (ToroVideoView) itemView.findViewById(R.id.video);
  }

  @Override public void bind(Object item) {
    if (!(item instanceof SimpleVideoObject)) {
      throw new IllegalStateException("Unexpected object: " + item.toString());
    }

    mVideoView.setVideoPath(((SimpleVideoObject) item).video);
  }

  @Override public boolean wantsToPlay() {
    Rect childRect = new Rect();
    itemView.getGlobalVisibleRect(childRect, new Point());
    int visibleHeight = childRect.bottom - childRect.top;
    // wants to play if user could see at lease 0.75 of video
    return visibleHeight > itemView.getHeight() * 0.75;
  }

  @Nullable @Override public Long getVideoId() {
    return (long) getAdapterPosition();
  }

  @Override public void onViewHolderBound() {
    super.onViewHolderBound();
    Picasso.with(itemView.getContext())
        .load(R.drawable.toro_place_holder)
        .fit()
        .centerInside()
        .into(mThumbnail);
    mInfo.setText("Bound");
  }

  @Override public void onPrepared(MediaPlayer mp) {
    super.onPrepared(mp);
    mInfo.setText("Prepared");
  }

  @Override public void onPlaybackStarted() {
    Log.e(TAG, toString() + " START PLAYBACK");
    mThumbnail.animate().alpha(0.f).setDuration(250).setListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationEnd(Animator animation) {
        VideoViewHolder.super.onPlaybackStarted();
      }
    }).start();
    mInfo.setText("Started");
  }

  @Override public void onPlaybackProgress(int position, int duration) {
    super.onPlaybackProgress(position, duration);
    Log.d(TAG, toString() + " position = [" + position + "], duration = [" + duration + "]");
    mInfo.setText(Util.timeStamp(position, duration));
  }

  @Override public void onPlaybackPaused() {
    Log.e(TAG, toString() + " PAUSE PLAYBACK");
    mThumbnail.animate().alpha(1.f).setDuration(250).setListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationEnd(Animator animation) {
        VideoViewHolder.super.onPlaybackPaused();
      }
    }).start();
    mInfo.setText("Paused");
  }

  @Override public void onPlaybackStopped() {
    Log.e(TAG, toString() + " STOP PLAYBACK");
    mThumbnail.animate().alpha(1.f).setDuration(250).setListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationEnd(Animator animation) {
        VideoViewHolder.super.onPlaybackStopped();
      }
    }).start();
    mInfo.setText("Completed");
  }

  @Override public void onPlaybackError(MediaPlayer mp, int what, int extra) {
    super.onPlaybackError(mp, what, extra);
    mThumbnail.animate().alpha(1.f).setDuration(250).setListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationEnd(Animator animation) {
        VideoViewHolder.super.onPlaybackStopped();
      }
    }).start();
    mInfo.setText("Error");
  }

  @Override public String toString() {
    return "Video: " + getVideoId();
  }
}