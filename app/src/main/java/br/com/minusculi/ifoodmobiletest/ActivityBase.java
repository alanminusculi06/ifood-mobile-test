package br.com.minusculi.ifoodmobiletest;

import android.annotation.SuppressLint;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("Registered")
public class ActivityBase extends AppCompatActivity {

    public void showAlertDialog(final String title, final String message, final DialogInterface.OnClickListener onPositiveClickListener) {
        runOnUiThread(() -> {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(title);
                builder.setMessage(message);
                builder.setPositiveButton(R.string.action_ok, onPositiveClickListener);
                builder.setCancelable(false);
                builder.show();
            } catch (Exception e) {
                //ignored
            }
        });
    }
}
