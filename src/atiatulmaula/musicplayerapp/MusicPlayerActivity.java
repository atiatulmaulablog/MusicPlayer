package atiatulmaula.musicplayerapp;

import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.MediaController;
import android.widget.MediaController.MediaPlayerControl;
import atiatulmaula.musicplayerapp.R;

/**
 * 
 * @author atiatulmaula
 *
 */
public class MusicPlayerActivity extends Activity implements MediaPlayerControl {
	private MediaController mMediaController;
	private MediaPlayer mMediaPlayer;
	private Handler mHandler = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.musicplayer_layout);

		mMediaPlayer = new MediaPlayer();
		mMediaController = new MediaController(this);
		mMediaController.setMediaPlayer(MusicPlayerActivity.this);
		mMediaController.setAnchorView(findViewById(R.id.audioView));

		String audioFile = "/sdcard/Music/lagu.mp3";
		try {
			mMediaPlayer.setDataSource(audioFile);
			mMediaPlayer.prepare();
		} catch (IOException e) {
			e.printStackTrace();
		}

		mMediaPlayer.setOnPreparedListener(new OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				mHandler.post(new Runnable() {
					public void run() {
						mMediaController.show(15000);
						mMediaPlayer.start();
					}
				});
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mMediaPlayer.stop();
		mMediaPlayer.release();
	}

	@Override
	public boolean canPause() {
		return true;
	}

	@Override
	public boolean canSeekBackward() {
		return false;
	}

	@Override
	public boolean canSeekForward() {
		return false;
	}

	@Override
	public int getBufferPercentage() {
		int percentage = (mMediaPlayer.getCurrentPosition() * 100)
				/ mMediaPlayer.getDuration();

		return percentage;
	}

	@Override
	public int getCurrentPosition() {
		return mMediaPlayer.getCurrentPosition();
	}

	@Override
	public int getDuration() {
		return mMediaPlayer.getDuration();
	}

	@Override
	public boolean isPlaying() {
		return mMediaPlayer.isPlaying();
	}

	@Override
	public void pause() {
		if (mMediaPlayer.isPlaying())
			mMediaPlayer.pause();
	}

	@Override
	public void seekTo(int position) {
		mMediaPlayer.seekTo(position);
	}

	@Override
	public void start() {
		mMediaPlayer.start();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mMediaController.show();
		return false;
	}

	@Override
	public int getAudioSessionId() {
		return 0;
	}
}
