package com.energy.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.energy.application.MyApplication;
import com.energy.bean.ResponseStatus;
import com.energy.bean.ResultDataResult;
import com.energy.bean.RoomDataResult;
import com.energy.bean.SiteDataResult;
import com.energy.util.Constant;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class Act_Graph extends BaseActivity {

	private String search;
	private Context context;
	private List data;

	public void onCreate(Bundle savedInstanceState) {
		MyApplication.getInstance().addActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_graph);
		init();
	}

	public void init() {
		context = this;
		Bundle bundle = this.getIntent().getExtras();
		search = bundle.getString("search");
		showGraph();
	}

	public void showGraph() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(search, new AsyncHttpResponseHandler() {
			public void onSuccess(String response) {
				ResponseStatus status = JSON.toJavaObject(
						JSON.parseObject(response), ResponseStatus.class);
				if (status.getStatus() == 200) {
					if (Constant.grade.equals("site")) {
						// 只存在SITE的形式
						SiteDataResult sitedata = JSON.toJavaObject(
								JSON.parseObject(response),
								SiteDataResult.class);
					} else if (Constant.grade.equals("room")
							|| Constant.grade.equals("building")) {
						// room的形式
						RoomDataResult roomdata = JSON.toJavaObject(
								JSON.parseObject(response),
								RoomDataResult.class);
						if (roomdata.getRoom_data().getData().size() > 0) {
							data = roomdata.getRoom_data().getData();
							showRoomGraph();
						} else {
							Toast.makeText(Act_Graph.this, "没有获取到数据！",
									Toast.LENGTH_SHORT).show();
						}

					} else {
						// 两者都存在的情况
						ResultDataResult resultdata = JSON.toJavaObject(
								JSON.parseObject(response),
								ResultDataResult.class);
					}
				} else {
					Toast.makeText(Act_Graph.this, "数据获取错误", Toast.LENGTH_SHORT)
							.show();
				}
			}

			public void onFailure(Throwable e, String response) {
				Toast.makeText(Act_Graph.this, "请检查网络！", Toast.LENGTH_SHORT)
						.show();
			}

			public void onFinish() {
			}
		});
	}

	// 在只有room的情况下显示机房的信息
	private void showRoomGraph() {
		Intent intent = ChartFactory.getLineChartIntent(this, getDataSet(),
				getRender());
		startActivity(intent);
	}

	private XYMultipleSeriesDataset getDataSet() {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

		XYSeries smps = new XYSeries("开关电源");
		for (int i = 0; i < data.size(); i++) {
			JSONObject jsobj = (JSONObject) data.get(i);
			smps.add(i,
					Double.parseDouble(jsobj.get("SMPS").toString()));
		}

		XYSeries cooling = new XYSeries("空调");
		for (int i = 0; i < data.size(); i++) {
			JSONObject jsobj = (JSONObject) data.get(i);
			cooling.add(i,
					Double.parseDouble(jsobj.get("cooling").toString()));
		}

		dataset.addSeries(smps);
		dataset.addSeries(cooling);

		return dataset;
	}

	public Double convertDate(String date) {

		try {
			Date time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(date);
			String s = new SimpleDateFormat("yyyyMMddhhmm").format(time);
			Log.e("时间", s);
			return Double.parseDouble(s);
		} catch (ParseException e) {
			e.printStackTrace();
			Log.e("错误",e.getMessage());
		}
		return 0d;
	}

	public String getTimestr(String date) {
		Date d = null;
		try {
			d = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String s = new SimpleDateFormat("yyyy-MM-dd hh:mm").format(d);
		return s;
	}

	private XYMultipleSeriesRenderer getRender() {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.setAxisTitleTextSize(16);
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(15);
		renderer.setLegendTextSize(15);
		renderer.setPointSize(5f);
		renderer.setMargins(new int[] { 20, 30, 15, 0 });
		renderer.setFitLegend(true);
		for (int i = 0; i < data.size(); i++) {
			JSONObject jsobj = (JSONObject) data.get(i);
			renderer.addXTextLabel(i,
					getTimestr(jsobj.get("date").toString()));
		}
		XYSeriesRenderer r = new XYSeriesRenderer();
		r.setColor(Color.BLUE);
		r.setPointStyle(PointStyle.SQUARE);
		r.setFillBelowLine(true);
		r.setFillBelowLineColor(Color.WHITE);
		r.setFillPoints(true);
		
		renderer.addSeriesRenderer(r);
		r = new XYSeriesRenderer();
		r.setPointStyle(PointStyle.CIRCLE);
		r.setColor(Color.GREEN);
		r.setFillPoints(true);
		
		renderer.addSeriesRenderer(r);
		renderer.setAxesColor(Color.DKGRAY);
		renderer.setLabelsColor(Color.LTGRAY);
		return renderer;
	}

}
