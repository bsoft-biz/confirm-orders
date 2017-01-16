package biz.bsoft.confirmorders;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import biz.bsoft.confirmorders.model.Order;

public class LoadOrdersFragment extends Fragment{

    public List<Order> objects;
    private TaskCallbacks taskCallbacks;
    private Task task;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        taskCallbacks = (TaskCallbacks) activity;
        //Log.d("Fragment","attachActivity");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        taskCallbacks = (TaskCallbacks) context;
        //Log.d("Fragment","attachContext");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retain this fragment across configuration changes.
        setRetainInstance(true);
        task = new Task();
        task.execute();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        taskCallbacks = null;
    }

    public interface TaskCallbacks {
        void onPreExecute();
        //void onProgressUpdate(int percent);
        //void onCancelled();
        void onPostExecute(List<Order> orders);
    }

    private class Task extends AsyncTask<Void,Void,List<Order>> {
        @Override
        protected void onPreExecute() {
            if (taskCallbacks != null) {
                taskCallbacks.onPreExecute();
            }
        }

        @Override
        protected List<Order> doInBackground(Void... voids) {
            return OrderRestTemplate.getOrders();
        }

        @Override
        protected void onPostExecute(List<Order> orders) {
            objects=orders;
            if (taskCallbacks != null) {
                taskCallbacks.onPostExecute(orders);
            }
        }
    }
}
