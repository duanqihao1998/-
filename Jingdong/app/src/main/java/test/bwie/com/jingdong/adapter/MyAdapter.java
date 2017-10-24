package test.bwie.com.jingdong.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import test.bwie.com.jingdong.Bean.San_FenLei;
import test.bwie.com.jingdong.R;

public class MyAdapter extends BaseAdapter{

	private Context context;
	private List<San_FenLei> list;
	
	public MyAdapter(Context context, List<San_FenLei> list) {
		super();
		this.context = context;
		this.list = list;
	}
	@Override
	public int getCount() {
		return list.size();
	}
	@Override
	public Object getItem(int position) {
		return list.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null){
			convertView=View.inflate(context, R.layout.listview_item, null);
			holder=new ViewHolder();
			holder.textView=convertView.findViewById(R.id.name);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.textView.setText(list.get(position).name);
		return convertView;
	}

	class ViewHolder{
		TextView textView;
	}
	
}
