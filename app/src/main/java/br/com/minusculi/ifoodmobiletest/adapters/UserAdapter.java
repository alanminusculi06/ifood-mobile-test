package br.com.minusculi.ifoodmobiletest.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import java.util.List;

import br.com.minusculi.ifoodmobiletest.R;
import br.com.minusculi.ifoodmobiletest.databinding.UserLayoutBinding;
import twitter4j.User;

public class UserAdapter extends BaseAdapter {

    private final List<User> users;
    private final LayoutInflater layoutInflater;

    public UserAdapter(Context context, List<User> users) {
        this.users = users;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") UserLayoutBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.user_layout, parent, false);
        convertView = binding.getRoot();
        binding.setUser(users.get(position));
        return convertView;
    }
}
