package com.appsriv.holbe.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appsriv.holbe.CominUpWithListview;
import com.appsriv.holbe.DrawerActivity;
import com.appsriv.holbe.ExpandListAdapterForItems;
import com.appsriv.holbe.R;
import com.appsriv.holbe.StickyHeader.HeaderListView;
import com.appsriv.holbe.StickyHeader.SectionAdapter;
import com.appsriv.holbe.models.Food;
import com.appsriv.holbe.models.Group;
import com.appsriv.holbe.models.LifeStyle;
import com.appsriv.holbe.models.Others;
import com.appsriv.holbe.models.Supplement;
import com.appsriv.holbe.models.Workout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SupplementFragment extends Fragment {
    private ExpandListAdapterForItems ExpAdapter;
    private ArrayList<Group> ExpListItems;
    private ExpandableListView ExpandList;
    public static ArrayList<Group> list;
    private int flag[];
    private int progressBarRes[];
    String name[];
    String lineColour[] = new String[]{"#ABD14B", "#3CC3AF", "#1AA2DF", "#AA68B4", "#BD345E"};
    TextView overalll_compliance;
    ArrayList<Workout> work_list = null;
    ArrayList<Supplement> sup_list = null;
    ArrayList<LifeStyle> life_list = null;
    ArrayList<Food> food_list = null;
    ArrayList<Others> other_list = null;

    HeaderListView headerListView;

    private ViewPager viewPager;
    int str_overalll_compliance;
    String workout_count, supplement_count, lifestyle_count, food_count, others_count;
    View view;
    SectionAdapter sectionAdapter;
    public HeaderListView getHeaderListView(){
        return headerListView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.treatment_with_section, container, false);
        //ExpandList = (ExpandableListView)view.findViewById(R.id.exp_list);
        headerListView = (HeaderListView) view.findViewWithTag("header_list");
        headerListView.setId(0);



    /*    DrawerActivity.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view1, int year, int monthOfYear, int dayOfMonth) {

                                SimpleDateFormat sdf = new SimpleDateFormat("EE");
                                Date d = new Date(year, monthOfYear, dayOfMonth - 1);
                                String dayOfTheWeek = sdf.format(d);
                                headerListView = (HeaderListView) view.findViewWithTag("header_list");
                                headerListView.setId(0);
                                DrawerActivity.date.setText(dayOfTheWeek + " " + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                //String url = "http://192.185.26.69/~holbe/api/patient/get_dashboard_new.php?id="+Login.details.get("userId")+"&dateid="+DrawerActivity.date.getText().toString().substring(4);
                                String URL = "http://192.185.26.69/~holbe/api/patient/get_coming_up.php?id=" + Login.details.get("userId") + "&dateid=" + DrawerActivity.date.getText().toString().substring(4);
                                Log.i("url", "url " + URL);
                                new AsyncHttpTask().execute(URL);
                                sectionAdapter.notifyDataSetChanged();
                                //sectionAdapter.notifyDataSetInvalidated();
                            }
                        }, DrawerActivity.year, DrawerActivity.month, DrawerActivity.day);


                dpd.show();


            }
        });*/
     /*   DrawerActivity.previousdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerActivity.c.add(Calendar.DATE, -1);
                DrawerActivity.formattedDate = DrawerActivity.df.format(DrawerActivity.c.getTime());
                headerListView = (HeaderListView) view.findViewWithTag("header_list");
                headerListView.setId(0);
                Log.v("PREVIOUS DATE : ", DrawerActivity.formattedDate);
                DrawerActivity.date.setText(DrawerActivity.formattedDate);
                String url = "http://192.185.26.69/~holbe/api/patient/get_coming_up.php?id=" + Login.details.get("userId") + "&dateid=" + DrawerActivity.date.getText().toString().substring(4);
                Log.i("url", "PREVIOUS url " + url);
                new AsyncHttpTask().execute(url);
                sectionAdapter.notifyDataSetChanged();
            }
        });


        //nextdate onlick
        DrawerActivity.nextdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DrawerActivity.c.add(Calendar.DATE, 1);
                DrawerActivity.formattedDate = DrawerActivity.df.format(DrawerActivity.c.getTime());
                headerListView = (HeaderListView) view.findViewWithTag("header_list");
                headerListView.setId(0);
                Log.v("NEXT DATE : ", DrawerActivity.formattedDate);
                DrawerActivity.date.setText(DrawerActivity.formattedDate);
                String url = "http://192.185.26.69/~holbe/api/patient/get_coming_up.php?id=" + Login.details.get("userId") + "&dateid=" + DrawerActivity.date.getText().toString().substring(4);
                //http://192.185.26.69/~holbe/api/patient/get_dashboard_new.php?id=1&dateid=2016-05-24
                Log.i("url", "nextdate url " + url);
                new AsyncHttpTask().execute(url);
                sectionAdapter.notifyDataSetChanged();
            }
        });
*/

        overalll_compliance = (TextView) view.findViewById(R.id.overalll_compliance);
        //String url = "http://192.185.26.69/~holbe/api/patient/get_treatment.php?id="+Login.details.get("userId");
        //String url = "http://192.185.26.69/~holbe/api/patient/get_dashboard_new.php?id="+Login.details.get("userId")+"&dateid="+DrawerActivity.date.getText().toString().substring(4);
        //Log.i("url", "url " +url);
//        String url = "http://192.185.26.69/~holbe/api/patient/get_coming_up.php?id=" + Login.details.get("userId") + "&dateid=" + DrawerActivity.date.getText().toString().substring(4);
      //  new AsyncHttpTask().execute(url);
       // Log.i("url", "first url " + url);

//        viewPager = (ViewPager) getActivity().findViewById(R.id.viewpager);
//        int index=viewPager.getCurrentItem();
//        smoothScrollToPositionFromTop(headerListView.getListView(),5);
//
//
//
//        Log.d("childcountlist", String.valueOf(headerListView.getListView().getChildCount()));
//        Log.d("childcountheader", String.valueOf(headerListView.getChildCount()));
//
//        Log.d("heightlist", String.valueOf(headerListView.getListView().getHeight()));
//
//
//        Log.d("listcount", String.valueOf(headerListView.getListView().getCount()));
//
//
//
//        headerListView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//                Log.d("childcountlist", String.valueOf(headerListView.getListView().getChildCount()));
//                Log.d("childcountheader", String.valueOf(headerListView.getChildCount()));
//
//                Log.d("heightlist", String.valueOf(headerListView.getListView().getHeight()));
//
//
//                Log.d("listcount", String.valueOf(headerListView.getListView().getCount()));
//            }
//        });
//
//
//
        return view;
    }
//
////    public static View getChildAtPosition(final AdapterView view, final int position) {
////        final int index = position - view.getFirstVisiblePosition();
////        if ((index >= 0) && (index < view.getChildCount())) {
////            return view.getChildAt(index);
////        } else {
////            return null;
////        }
////    }
////
//    public static void smoothScrollToPositionFromTop(final AbsListView view, final int position) {
//       // View child = getChildAtPosition(view, position);
//        // There's no need to scroll if child is already at top or view is already scrolled to its end
////        if ((child != null) && ((child.getTop() == 0) || ((child.getTop() > 0) && !view.canScrollVertically(1)))) {
////            Log.d("return","return");
////            return;
////        }
//
//        view.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(final AbsListView view, final int scrollState) {
//                if (scrollState == SCROLL_STATE_IDLE) {
//                    view.setOnScrollListener(null);
//
//                    // Fix for scrolling bug
//                    new Handler().post(new Runnable() {
//                        @Override
//                        public void run() {
//                            view.setSelection(position);
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount,
//                                 final int totalItemCount) { }
//        });
//
//        // Perform scrolling to position
////        view.clearFocus();
////        new Handler().post(new Runnable() {
////            @Override
////            public void run() {
////                view.setSelection(position);
////            }
////        });
    //}



    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(getActivity(), "Please wait", "Loading...");
        }

        @Override
        protected Integer doInBackground(String... params) {
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                /* forming th java.net.URL object */
                URL url6 = new URL(params[0]);

                urlConnection = (HttpURLConnection) url6.openConnection();

                /* for Get request */
                urlConnection.setRequestMethod("GET");

                int statusCode = urlConnection.getResponseCode();

                /* 200 represents HTTP OK */
                if (statusCode == 200) {
                    System.out.println("Status code is:" + statusCode);
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    System.out.println("this is response" + response.toString());

                    parseResult(response.toString());


                    result = 1; // Successful

                } else {

                    result = 0; //"Failed to fetch data!"// ;
                    System.out.print("unable to fetch data");
                }

            } catch (Exception e) {


                //  Log.d(TAG, e.getLocalizedMessage());
            } finally {
                if (urlConnection != null) {

                }
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {

            //mProgressDialog.dismiss();

            /* Download complete. Lets update UI */
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (result == 1) {
                //Log.e(TAG, "fetch data!");

               /* adapter6 = new MyRecyclerAdapter6(SearchActivity.this,feedItemList6);
                HRecyclerView.setAdapter(adapter6);*/

                DrawerActivity.top.setText(supplement_count);
                DrawerActivity.top1.setText(workout_count);
                DrawerActivity.top2.setText(lifestyle_count);
                DrawerActivity.top3.setText(food_count);
                DrawerActivity.top4.setText(others_count);


                overalll_compliance.setText("" + str_overalll_compliance + "%");
                /*ExpAdapter= new ExpandListAdapterForItems(getActivity(),list,ExpandList,progressBarRes);
				ExpandList.setAdapter(ExpAdapter);
				ExpAdapter.notifyDataSetChanged();
				ExpandList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
					@Override
					public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
					{

						Intent intent = new Intent(getActivity(),CominUpWithListview.class);
						intent.putExtra("groupPosition",groupPosition);
						intent.putExtra("childPosition",childPosition);
						startActivity(intent);
						//startActivity(new Intent(getActivity(),CominUpWithListview.class));
						return false;
					}
				});*/



				/*headerListView = (HeaderListView)view.findViewWithTag("header_list");
				headerListView.setId(0);

				headerListView.getListView().deferNotifyDataSetChanged();*/
                sectionAdapter = new SectionAdapter() {
                    @Override
                    public int numberOfSections() {
                        return list.size();
                    }

                    @Override
                    public int numberOfRows(int section) {
                        //return 5;

                        int size = 0;

                        if (section == 1) {
                            ArrayList<Workout> chList = SupplementFragment.list.get(section).getItems();
                            size = chList.size();
                        } else if (section == 0) {
                            ArrayList<Supplement> chList = SupplementFragment.list.get(section).getSup_Items();
                            size = chList.size();
                        } else if (section == 2) {
                            ArrayList<LifeStyle> chList = SupplementFragment.list.get(section).getLife_Items();
                            size = chList.size();
                        } else if (section == 3) {
                            ArrayList<Food> chList = SupplementFragment.list.get(section).getFood_Items();
                            size = chList.size();
                        } else if (section == 4) {
                            ArrayList<Others> chList = SupplementFragment.list.get(section).getOther_Items();
                            size = chList.size();
                        }
                        return size;

                    }

                    @Override
                    public Object getRowItem(int section, int row) {
                        return null;
                    }

                    @Override
                    public boolean hasSectionHeaderView(int section) {
                        return true;
                    }


                    public Object getChild(int groupPosition, int childPosition) {
                        ArrayList<Workout> chList = SupplementFragment.list.get(groupPosition).getItems();
                        return chList.get(childPosition);
                    }

                    public Object getChild1(int groupPosition, int childPosition) {

                        ArrayList<Supplement> chList = SupplementFragment.list.get(groupPosition).getSup_Items();
                        return chList.get(childPosition);
                    }

                    public Object getChild2(int groupPosition, int childPosition) {

                        ArrayList<LifeStyle> chList = SupplementFragment.list.get(groupPosition).getLife_Items();
                        return chList.get(childPosition);
                    }

                    public Object getChild3(int groupPosition, int childPosition) {
                        ArrayList<Food> chList = SupplementFragment.list.get(groupPosition).getFood_Items();
                        return chList.get(childPosition);
                    }

                    public Object getChild4(int groupPosition, int childPosition) {
                        ArrayList<Others> chList = SupplementFragment.list.get(groupPosition).getOther_Items();
                        return chList.get(childPosition);
                    }

                    public Object getGroup(int groupPosition) {
//						if (groupPosition==5)
//						{
//							Log.d("position", String.valueOf(groupPosition-1)+String.valueOf(groupPosition));
//							return SupplementFragment.list.get(groupPosition-1);
//
//						}
//						else
//						{
                        //return SupplementFragment.list.get(4);
                        //Log.d("position", String.valueOf(groupPosition - 1) + String.valueOf(groupPosition));
                        return SupplementFragment.list.get(groupPosition);
//						}


                    }


                    @Override
                    public View getRowView(int section, int row, View convertView, ViewGroup parent) {

                        Workout workout = null;
                        Supplement supplement = null;
                        LifeStyle style = null;
                        Food food = null;
                        Others others = null;

                        if (section == 0) {
                            supplement = (Supplement) getChild1(section, row);
                        } else if (section == 1) {
                            workout = (Workout) getChild(section, row);
                        } else if (section == 2) {
                            style = (LifeStyle) getChild2(section, row);
                        } else if (section == 3) {
                            food = (Food) getChild3(section, row);
                        } else if (section == 4) {
                            others = (Others) getChild4(section, row);
                        }


//                        if (convertView == null) {//gives wrong group position while scrolling and group position 1 repeats mixing up the data hence commented
                            //   convertView = (TextView) getLayoutInflater().inflate(getResources().getLayout(android.R.layout.simple_list_item_1), null);
                            LayoutInflater infalInflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
                            convertView = infalInflater.inflate(R.layout.child_item, null);
//                        }
                        TextView exc_name = (TextView) convertView.findViewById(R.id.exc_name_child);
                        TextView rep = (TextView) convertView.findViewById(R.id.rep_child);//reps,amount,when for food,gap n freq for lifestyle
                        TextView time = (TextView) convertView.findViewById(R.id.time_child);//time header
                        TextView sets = (TextView) convertView.findViewById(R.id.sets_child);//sets
                        TextView weight = (TextView) convertView.findViewById(R.id.weight_child);//weight
                        TextView rest = (TextView) convertView.findViewById(R.id.rest_child);//rest
                        TextView gap = (TextView) convertView.findViewById(R.id.gap_child);///gap n freequency
                        TextView compliance_percentage = (TextView) convertView.findViewById(R.id.compliance_percentage);
                        TextView colour = (TextView) convertView.findViewById(R.id.colour);
                        ProgressBar circle_progress_bar_front = (ProgressBar) convertView.findViewById(R.id.circle_progress_bar);
                        ProgressBar circle_progress_bar_back = (ProgressBar) convertView.findViewById(R.id.circle_progress_bar_back);
                        //((TextView) convertView).setText("Section " + section + " Row " + row);


                        if (section == 0) {

                            colour.setBackgroundColor(Color.parseColor(supplement.getColour()));
                            exc_name.setText(supplement.getSupplement_name());
                            time.setText(supplement.getTime());
                            rep.setText("amount: "+supplement.getAmount());
                            gap.setText("gap: "+stringconcat(supplement.getFreequency(),supplement.getGap()));
                            compliance_percentage.setText(supplement.getCompliance());
                            circle_progress_bar_front.setBackgroundResource(supplement.getProgressBarRes());
                            circle_progress_bar_front.setProgressDrawable(getActivity().getResources().getDrawable(supplement.getProgressBarRes()));
                            circle_progress_bar_front.setProgress(SupplementFragment.list.get(0).getSup_Items().get(row).getInt_compliance());

                        }

                        if (section == 1) {

                            colour.setBackgroundColor(Color.parseColor(workout.getColour()));
                            exc_name.setText(workout.getWorkout_name());
                            time.setText(workout.getTime());
                            rep.setText("reps: "+workout.getReps());
                            sets.setText("sets: "+workout.getSets());
                            gap.setText("gap: " + stringconcat(workout.getFreequency(),workout.getGap()));
                            weight.setText("weight: "+workout.getWeight());
                            rest.setText("rest: "+workout.getRest());

                            compliance_percentage.setText(workout.getCompliance());
                            circle_progress_bar_front.setBackgroundResource(workout.getProgressBarRes());
                            circle_progress_bar_front.setProgressDrawable(getActivity().getResources().getDrawable(workout.getProgressBarRes()));
                            //circle_progress_bar_front.setProgress(workout.getInt_compliance());
                            circle_progress_bar_front.setProgress(SupplementFragment.list.get(1).getItems().get(row).getInt_compliance());
                        }
                        if (section == 2) {
                            colour.setBackgroundColor(Color.parseColor(style.getColour()));
                            exc_name.setText(style.getLifestyle_name());
                            time.setText(style.getTime()+" minutes");
                            rep.setText("gap: "+stringconcat(style.getFreequency(),style.getGap()));

                            compliance_percentage.setText(style.getCompliance());
                            circle_progress_bar_front.setBackgroundResource(style.getProgressBarRes());
                            circle_progress_bar_front.setProgressDrawable(getActivity().getResources().getDrawable(style.getProgressBarRes()));
                            //circle_progress_bar_front.setProgress(style.getInt_compliance());
                            circle_progress_bar_front.setProgress(SupplementFragment.list.get(2).getLife_Items().get(row).getInt_compliance());
                        }
                        if (section == 3) {
                            colour.setBackgroundColor(Color.parseColor(food.getColour()));
                            exc_name.setText(food.getFood_name());
                            rep.setText("when: "+food.getWhen());
                            time.setText(food.getTime());
                            compliance_percentage.setText(food.getCompliance());
                            circle_progress_bar_front.setBackgroundResource(food.getProgressBarRes());
                            circle_progress_bar_front.setProgressDrawable(getActivity().getResources().getDrawable(food.getProgressBarRes()));
                            //circle_progress_bar_front.setProgress(food.getInt_compliance());
                            circle_progress_bar_front.setProgress(SupplementFragment.list.get(3).getFood_Items().get(row).getInt_compliance());
                        }
                        if (section == 4) {
                            colour.setBackgroundColor(Color.parseColor(others.getColour()));
                            exc_name.setText(others.getOthers_name());
                            rep.setText("duration: "+others.getDuration());
                            compliance_percentage.setText(others.getCompliance());
                            circle_progress_bar_front.setBackgroundResource(others.getProgressBarRes());
                            circle_progress_bar_front.setProgressDrawable(getActivity().getResources().getDrawable(others.getProgressBarRes()));
                            //circle_progress_bar_front.setProgress(others.getInt_compliance());
                            circle_progress_bar_front.setProgress(SupplementFragment.list.get(4).getOther_Items().get(row).getInt_compliance());
                        }


                        return convertView;
                    }

                    @Override
                    protected int getSection(int position) {
                        return super.getSection(position);
                    }

                    @Override
                    public int getSectionHeaderViewTypeCount() {
                        return 2;
                    }

                    @Override
                    public int getSectionHeaderItemViewType(int section) {
                        return section % 2;
                    }

                    @Override
                    public Object getSectionHeaderItem(int section) {
                        return super.getSectionHeaderItem(section);
                    }

                    @Override
                    protected int getRowInSection(int position) {
                        return super.getRowInSection(position);
                    }


                    @Override
                    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
						/*if (section<5)
						{*/

//						if(section==5){
//							Log.d("index", String.valueOf(section));
//							section=section-1;
//						}else {
                        //Log.d("index", String.valueOf(section));
                        Group group = (Group) getGroup(section);
//                        if (convertView == null) {//gives wrong group position while scrolling and group position 1 repeats mixing up the data hence commented
                            LayoutInflater inf = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
                            convertView = inf.inflate(R.layout.group_item, null);

								/*
								if (getSectionHeaderItemViewType(section) == 0) {
									LayoutInflater inf = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
									convertView = inf.inflate(R.layout.group_item, null);
								} else {
									LayoutInflater inf = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
									convertView = inf.inflate(R.layout.group_item, null);
								}*/
//                        }
                        ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
                        icon.setBackgroundResource(group.getIcon());
                        TextView type = (TextView) convertView.findViewById(R.id.type);
                        type.setText(group.getName());
                        //type.setTextColor(Color.parseColor(SupplementFragment.list.get(section).getItems().get(section).getColour()));
                        type.setTextColor(Color.parseColor(lineColour[section]));
                        TextView compliance_percentage = (TextView) convertView.findViewById(R.id.compliance_percentage);
                        if (section == 0) {
                            compliance_percentage.setText("" + group.getSupplement_compliance() + "%");
                        } else if (section == 1) {
                            compliance_percentage.setText("" + group.getWorkout_compliance() + "%");
                        } else if (section == 2) {
                            compliance_percentage.setText("" + group.getLifestyle_compliance() + "%");
                        } else if (section == 3) {
                            compliance_percentage.setText("" + group.getFood_compliance() + "%");
                        } else if (section == 4) {
                            compliance_percentage.setText("" + group.getOthers_compliance() + "%");
                        }

						/*	if (getSectionHeaderItemViewType(section) == 0)
							{

								ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
								icon.setBackgroundResource(group.getIcon());
								TextView type = (TextView) convertView.findViewById(R.id.type);
								type.setText(group.getName());
								//type.setTextColor(Color.parseColor(SupplementFragment.list.get(section).getItems().get(section).getColour()));
								type.setTextColor(Color.parseColor(lineColour[section]));
								TextView compliance_percentage = (TextView) convertView.findViewById(R.id.compliance_percentage);
								if (section == 0) {
									compliance_percentage.setText("" + group.getSupplement_compliance() + "%");
								} else if (section == 1) {
									compliance_percentage.setText("" + group.getWorkout_compliance() + "%");
								} else if (section == 2) {
									compliance_percentage.setText("" + group.getLifestyle_compliance() + "%");
								} else if (section == 3) {
									compliance_percentage.setText("" + group.getFood_compliance() + "%");
								} else if (section == 4) {
									compliance_percentage.setText("" + group.getOthers_compliance() + "%");
								}

							} else
							{
								ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
								icon.setBackgroundResource(group.getIcon());
								TextView type = (TextView) convertView.findViewById(R.id.type);
								type.setText(group.getName());
								//type.setTextColor(Color.parseColor(SupplementFragment.list.get(section).getItems().get(section).getColour()));
								type.setTextColor(Color.parseColor(lineColour[section]));
								TextView compliance_percentage = (TextView) convertView.findViewById(R.id.compliance_percentage);
								if (section == 0) {
									compliance_percentage.setText("" + group.getSupplement_compliance() + "%");
								} else if (section == 1) {
									compliance_percentage.setText("" + group.getWorkout_compliance() + "%");
								} else if (section == 2) {
									compliance_percentage.setText("" + group.getWorkout_compliance() + "%");
								} else if (section == 3) {
									compliance_percentage.setText("" + group.getFood_compliance() + "%");
								} else if (section == 4) {
									compliance_percentage.setText("" + group.getOthers_compliance() + "%");
								}


								switch (section) {
									case 0:
										compliance_percentage.setText("" + group.getSupplement_compliance() + "%");
										break;
									case 1:
										compliance_percentage.setText("" + group.getWorkout_compliance() + "%");
										break;
									case 2:
										compliance_percentage.setText("" + group.getWorkout_compliance() + "%");
										break;
									case 3:
										convertView.setBackgroundColor(Color.parseColor("#eef2f6"));
										break;
									case 4:
										convertView.setBackgroundColor(Color.parseColor("#eef2f6"));

								}
							}*/

                        switch (section) {
                            case 0:
                                convertView.setBackgroundColor(Color.parseColor("#eef2f6"));
                                break;
                            case 1:
                                convertView.setBackgroundColor(Color.parseColor("#eef2f6"));
                                break;
                            case 2:
                                convertView.setBackgroundColor(Color.parseColor("#eef2f6"));
                                break;
                            case 3:
                                convertView.setBackgroundColor(Color.parseColor("#eef2f6"));
                                break;
                            case 4:
                                convertView.setBackgroundColor(Color.parseColor("#eef2f6"));

							/*}*/
                        }
//						}
                        return convertView;
                    }


                    @Override
                    public void onRowItemClick(AdapterView<?> parent, View view, int section, int row, long id) {
                        super.onRowItemClick(parent, view, section, row, id);
                        Intent intent = new Intent(getActivity(), CominUpWithListview.class);
                        intent.putExtra("groupPosition", section);
                        intent.putExtra("childPosition", row);
                        startActivity(intent);
                    }
                };
                //sectionAdapter.notifyDataSetChanged();
                headerListView.setAdapter(sectionAdapter);
                sectionAdapter.notifyDataSetChanged();


                //getActivity().setContentView(list);
                //startActivity(new Intent(getActivity(), DemoActivity.class));


                // Toast.makeText(getApplicationContext(), "your search results", Toast.LENGTH_LONG).show();
            } else {

                Log.e("fail", "Failed to fetch data!");
                //Toast.makeText(, "", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void parseResult(String result) {
        list = new ArrayList<>();
        flag = new int[]{R.drawable.circle_suppliments, R.drawable.circle_workout, R.drawable.circle_heart, R.drawable.circlefoodanddrink,
                R.drawable.circle_others};
        progressBarRes = new int[]
                {R.drawable.supplement_circle_progress_foregrnd, R.drawable.workout_circle_progress_foregrnd, R.drawable.lifestylr_circle_progress_foregrnd,
                        R.drawable.foodanddrinks_circle_progress_foregrnd,
                        R.drawable.others_circle_progress_foregrnd};

        name = new String[]{"Supplements", "Workout", "Lifestyle", "Food & Drinks", "Others"};

        lineColour = new String[]{"#ABD14B", "#3CC3AF", "#1AA2DF", "#AA68B4", "#BD345E"};

        try {

            JSONObject object = new JSONObject(result);

            if (object.length() != 0) {

                list = new ArrayList<>();
                work_list = new ArrayList<>();
                life_list = new ArrayList<>();
                sup_list = new ArrayList<>();
                food_list = new ArrayList<>();
                other_list = new ArrayList<>();

                for (int j = 0; j < 5; j++) {
                    Group gru = new Group();
                    gru.setName(name[j]);
                    gru.setIcon(flag[j]);
                    gru.setPercentage("91");
                    gru.setSectionName("section" + j);

                    work_list = new ArrayList<Workout>();

					/*	for (int i = 0; i < 1; i++)
						{
*/

//                    //treatment count
                    JSONArray array = object.getJSONArray("treatment_count");
                    for (int k = 0; k < array.length(); k++) {

                        workout_count = array.getJSONObject(k).getString("workout_count");
                        supplement_count = array.getJSONObject(k).getString("supplement_count");
                       lifestyle_count = array.getJSONObject(k).getString("lifestyle_count");
                        food_count = array.getJSONObject(k).getString("food_count");
                        others_count = array.getJSONObject(k).getString("others_count");
                    }
                    //over all compliance
                    JSONArray overAllCompArr = object.getJSONArray("overall_compliance");
                    str_overalll_compliance = overAllCompArr.getJSONObject(0).getInt("overall_compliance");
                    //workout

                    Workout work;
                    JSONArray workout = object.getJSONArray("workout");
                    for (int x = 0; x < workout.length(); x++) {
                        work = new Workout();
                        work.setTime(workout.getJSONObject(x).getString("time"));
                        work.setTimings_id(workout.getJSONObject(x).getString("timings_id"));
                        work.setWorkout_mapping_id(workout.getJSONObject(x).getString("workout_mapping_id"));
                        work.setReps(workout.getJSONObject(x).getString("reps"));
                        work.setSets(workout.getJSONObject(x).getString("sets"));
                        work.setWeight(workout.getJSONObject(x).getString("weight"));
                        work.setGap(workout.getJSONObject(x).getString("gap"));
                        work.setFreequency(workout.getJSONObject(x).getString("frequency"));
                        work.setRest(workout.getJSONObject(x).getString("rest"));
                        work.setWorkout_name(workout.getJSONObject(x).getString("workout_name"));
                        work.setCompliance(workout.getJSONObject(x).getString("compliance") + "%");
                        work.setInt_compliance(workout.getJSONObject(x).getInt("compliance"));
                        work.setProgressBarRes(progressBarRes[j]);
                        work.setColour(lineColour[j]);
                        work_list.add(work);
                    }

                    // supplement

                    Supplement supp = null;
                    sup_list = new ArrayList<>();
                    JSONArray supplement = object.getJSONArray("supplement");
                    for (int x = 0; x < supplement.length(); x++) {
                        supp = new Supplement();
                        supp.setTime(supplement.getJSONObject(x).getString("time"));
                        supp.setTimings_id(supplement.getJSONObject(x).getString("timings_id"));
                        supp.setSupplement_mapping_id(supplement.getJSONObject(x).getString("supplement_mapping_id"));
                        supp.setSupplement_name(supplement.getJSONObject(x).getString("supplement_name"));
                        supp.setAmount(supplement.getJSONObject(x).getString("amount"));
                        supp.setGap(supplement.getJSONObject(x).getString("gap"));
                        //supp.setRepitition(supplement.getJSONObject(x).getString("repitition"));
                        supp.setCompliance(supplement.getJSONObject(x).getString("compliance") + "%");
                       // supp.setWhen_time(supplement.getJSONObject(x).getString("when_time"));
                        supp.setInt_compliance(supplement.getJSONObject(x).getInt("compliance"));
                        supp.setFreequency(supplement.getJSONObject(x).getString("frequency"));
                        supp.setProgressBarRes(progressBarRes[j]);
                        supp.setColour(lineColour[j]);
                        sup_list.add(supp);
                    }

                    //Food

                    Food food1 = null;
                    food_list = new ArrayList<>();
                    JSONArray food = object.getJSONArray("food");
                    for (int x = 0; x < food.length(); x++) {
                        food1 = new Food();
                        food1.setTime(food.getJSONObject(x).getString("time"));
                        food1.setTimings_id(food.getJSONObject(x).getString("timings_id"));
                        food1.setFood_mapping_id(food.getJSONObject(x).getString("food_mapping_id"));
                        food1.setFood_name(food.getJSONObject(x).getString("food_name"));
                        food1.setWhen(food.getJSONObject(x).getString("when"));
                        food1.setCompliance(food.getJSONObject(x).getString("compliance") + "%");
                        food1.setInt_compliance(food.getJSONObject(x).getInt("compliance"));
                        food1.setProgressBarRes(progressBarRes[j]);
                        food1.setColour(lineColour[j]);
                        food_list.add(food1);
                    }

                    //lifestyle

                    life_list = new ArrayList<>();
                    LifeStyle style = null;
                    JSONArray lifestyle = object.getJSONArray("lifestyle");
                    for (int x = 0; x < lifestyle.length(); x++) {
                        style = new LifeStyle();
                        style.setTime(lifestyle.getJSONObject(x).getString("time"));
                        style.setTimings_id(lifestyle.getJSONObject(x).getString("timings_id"));
                        style.setLifestyle_mapping_id(lifestyle.getJSONObject(x).getString("lifestyle_mapping_id"));
                        style.setLifestyle_name(lifestyle.getJSONObject(x).getString("lifestyle_name"));
                        style.setTime(lifestyle.getJSONObject(x).getString("time"));
                        //style.setRepitition(lifestyle.getJSONObject(x).getString("repitition"));
                        //style.setWhen(lifestyle.getJSONObject(x).getString("when"));
                        style.setCompliance(lifestyle.getJSONObject(x).getString("compliance") + "%");
                        style.setInt_compliance(lifestyle.getJSONObject(x).getInt("compliance"));
                        style.setGap(lifestyle.getJSONObject(x).getString("gap"));
                        style.setFreequency(lifestyle.getJSONObject(x).getString("frequency"));
                        style.setProgressBarRes(progressBarRes[j]);
                        style.setColour(lineColour[j]);
                        life_list.add(style);
                    }

                    //others
                    Others other = null;
                    other_list = new ArrayList<>();
                    JSONArray others = object.getJSONArray("others");
                    for (int x = 0; x < others.length(); x++) {
                        other = new Others();
                        other.setTime(others.getJSONObject(x).getString("time"));
                        other.setTimings_id(others.getJSONObject(x).getString("timings_id"));
                        other.setOthers_mapping_id(others.getJSONObject(x).getString("others_mapping_id"));
                        other.setOthers_name(others.getJSONObject(x).getString("others_name"));
                        other.setDuration(others.getJSONObject(x).getString("duration"));
                        other.setCompliance(others.getJSONObject(x).getString("compliance") + "%");
                        other.setInt_compliance(others.getJSONObject(x).getInt("compliance"));
                        other.setProgressBarRes(progressBarRes[j]);
                        other.setColour(lineColour[j]);
                        other_list.add(other);
                    }


//                    JSONArray workout_compliance = object.getJSONArray("workout_compliance");
//                    gru.setWorkout_compliance(workout_compliance.getJSONObject(0).getInt("workout_compliance"));
//
//                    JSONArray supplement_compliance = object.getJSONArray("supplement_compliance");
//                    gru.setSupplement_compliance(supplement_compliance.getJSONObject(0).getInt("supplement_compliance"));
//
//                    JSONArray lifestyle_compliance = object.getJSONArray("lifestyle_compliance");
//                    gru.setLifestyle_compliance(lifestyle_compliance.getJSONObject(0).getInt("lifestyle_compliance"));
//
//                    JSONArray food_compliance = object.getJSONArray("food_compliance");
//                    gru.setFood_compliance(food_compliance.getJSONObject(0).getInt("food_compliance"));
//
//                    JSONArray others_compliance = object.getJSONArray("others_compliance");
//                    gru.setOthers_compliance(others_compliance.getJSONObject(0).getInt("others_compliance"));
						/*}*/

                    gru.setItems(work_list);
                    gru.setFood_Items(food_list);
                    gru.setLife_Items(life_list);
                    gru.setSup_Items(sup_list);
                    gru.setOther_Items(other_list);
                    list.add(gru);


                }


            } else {

            }

        } catch (JSONException j) {
            j.printStackTrace();
        }

    }
    private String stringconcat(String freq,String gap){
        String frequency = "once";
        switch(freq){
            case "1":frequency="Once";
                break;
            case "2":frequency="Twice";
                break;
            case "3":frequency="Thrice";
                break;
        }
        String result = frequency+" per "+gap;
        return result;
    }
}

