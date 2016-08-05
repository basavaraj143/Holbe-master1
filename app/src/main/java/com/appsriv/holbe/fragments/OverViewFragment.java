package com.appsriv.holbe.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.appsriv.holbe.Adatpters.ExpandListAdapterForOverItems;
import com.appsriv.holbe.R;
import com.appsriv.holbe.models.Treatment;
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

public class OverViewFragment extends Fragment
{
	private int[] background;
	ExpandListAdapterForOverItems adapterForOverItems;
	ExpandableListView listView;

	ArrayList<Group> groups = new ArrayList<>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{

		View view = inflater.inflate(R.layout.overview_list, container, false);
		listView = (ExpandableListView)view.findViewById(R.id.exp_list);
		String url = "http://192.185.26.69/~holbe/api/patient/getoverview.php?id=1";
		new AsyncHttpTask().execute(url);
		/*background = new int[] { R.drawable.circle_suppliments, R.drawable.circle_workout, R.drawable.circle_heart, R.drawable.circlefoodanddrink,
				R.drawable.circle_others};
		adapterForOverItems = new ExpandListAdapterForOverItems(getActivity(),groups,listView,background);
		listView.setAdapter(adapterForOverItems);*/
		return view;
	}
	public class AsyncHttpTask extends AsyncTask<String, Void, Integer>
	{

		ProgressDialog progressDialog;
		@Override
		protected void onPreExecute() {

			progressDialog = ProgressDialog.show(getActivity(),"Please wait","Loading...");
		}

		@Override
		protected Integer doInBackground(String... params)
		{
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
				if (statusCode ==  200) {
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

				}else{

					result = 0; //"Failed to fetch data!"// ;
					System.out.print("unable to fetch data");
				}

			} catch (Exception e) {


				//  Log.d(TAG, e.getLocalizedMessage());
			}
			finally {
				if (urlConnection!=null)
				{

				}
			}

			return result; //"Failed to fetch data!";
		}

		@Override
		protected void onPostExecute(Integer result)
		{


			if (result == 1)
			{
				if (progressDialog!=null&&progressDialog.isShowing())
				{
					progressDialog.dismiss();
				}

				background = new int[] { R.drawable.circle_suppliments, R.drawable.circle_workout, R.drawable.circle_heart, R.drawable.circlefoodanddrink,
						R.drawable.circle_others};
				adapterForOverItems = new ExpandListAdapterForOverItems(getActivity(),groups,listView,background);
				listView.setAdapter(adapterForOverItems);

			} else {

				// Log.e(TAG, "Failed to fetch data!");
			}
		}
	}
	private void parseResult(String result)
	{
		try
		{
			Group group = new Group();

			for (int x =0; x<6;x++ ) {
				group = new Group();

				JSONObject object = new JSONObject(result);
				JSONArray treat = object.getJSONArray("treatment");
				Treatment treatment = null;
				ArrayList<Treatment> treatments = new ArrayList<>();
				for (int i = 0; i < treat.length(); i++) {
					treatment = new Treatment();
					treatment.setTreatment_count(treat.getJSONObject(i).getInt("treatment_count"));
					treatment.setTreatment_completed(treat.getJSONObject(i).getInt("treatment_completed"));
					treatment.setTreatment_late(treat.getJSONObject(i).getInt("treatment_late"));
					treatment.setTreatment_missed(treat.getJSONObject(i).getInt("treatment_missed"));
					treatments.add(treatment);
				}

				group.setTreatments(treatments);

				JSONArray supp = object.getJSONArray("supplement");
				Supplement supplement = null;
				ArrayList<Supplement> supplements = new ArrayList<>();
				for (int i = 0; i < supp.length(); i++) {

					supplement = new Supplement();
					supplement.setSupplement_count(supp.getJSONObject(i).getInt("supplement_count"));
					supplement.setSupplement_completed(supp.getJSONObject(i).getInt("supplement_completed"));
					supplement.setSupplement_late(supp.getJSONObject(i).getInt("supplement_late"));
					supplement.setSupplement_missed(supp.getJSONObject(i).getInt("supplement_missed"));
					supplements.add(supplement);

				}
				group.setSup_Items(supplements);


				JSONArray work = object.getJSONArray("workout");
				Workout workout = null;
				ArrayList<Workout> workouts = new ArrayList<>();
				for (int i = 0; i < work.length(); i++) {
					workout = new Workout();
					workout.setWorkout_count(work.getJSONObject(i).getInt("workout_count"));
					workout.setWorkout_completed(work.getJSONObject(i).getInt("workout_completed"));
					workout.setWorkout_late(work.getJSONObject(i).getInt("workout_late"));
					workout.setWorkout_missed(work.getJSONObject(i).getInt("workout_missed"));
					workouts.add(workout);
				}
				group.setItems(workouts);


				JSONArray food1 = object.getJSONArray("food");
				Food food = null;
				ArrayList<Food> foods = new ArrayList<>();
				for (int i = 0; i < food1.length(); i++) {
					food = new Food();
					food.setFood_count(food1.getJSONObject(i).getInt("food_count"));
					food.setFood_completed(food1.getJSONObject(i).getInt("food_completed"));
					food.setFood_late(food1.getJSONObject(i).getInt("food_late"));
					food.setFood_missed(food1.getJSONObject(i).getInt("food_missed"));
					foods.add(food);
				}
				group.setFood_Items(foods);

				JSONArray lifestyle = object.getJSONArray("lifestyle");
				LifeStyle style = null;
				ArrayList<LifeStyle> lifeStyles = new ArrayList<>();
				for (int i = 0; i < lifestyle.length(); i++) {
					style = new LifeStyle();
					style.setLifestyle_count(lifestyle.getJSONObject(i).getInt("lifestyle_count"));
					style.setLifestyle_completed(lifestyle.getJSONObject(i).getInt("lifestyle_completed"));
					style.setLifestyle_late(lifestyle.getJSONObject(i).getInt("lifestyle_late"));
					style.setLifestyle_missed(lifestyle.getJSONObject(i).getInt("lifestyle_missed"));
					lifeStyles.add(style);

				}
				group.setLife_Items(lifeStyles);


				JSONArray other = object.getJSONArray("others");
				Others others = null;
				ArrayList<Others> otherses = new ArrayList<>();
				for (int i = 0; i < other.length(); i++) {
					others = new Others();
					others.setOthers_count(other.getJSONObject(i).getInt("others_count"));
					others.setOthers_completed(other.getJSONObject(i).getInt("others_completed"));
					others.setOthers_late(other.getJSONObject(i).getInt("others_late"));
					others.setOthers_missed(other.getJSONObject(i).getInt("others_missed"));
					otherses.add(others);
				}
				group.setOther_Items(otherses);
				groups.add(group);
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}


	}



}
