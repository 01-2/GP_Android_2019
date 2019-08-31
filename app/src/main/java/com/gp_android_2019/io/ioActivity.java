package com.gp_android_2019.io;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.gp_android_2019.MainActivity;
import com.gp_android_2019.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ioActivity extends AppCompatActivity {
    private static final String TAG = ioActivity.class.getSimpleName();
    // �޴� KEY
    private final int MENU_DOWNLOAD = 0;
    private final int MENU_ALL = 1;
    private int MENU_MODE = MENU_DOWNLOAD;

    private PackageManager pm;

    private View mLoadingContainer;
    private ListView mListView = null;
    private IAAdapter mAdapter = null;

    private int runCheck = 0;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_io);

        mLoadingContainer = findViewById(R.id.loading_container);
        mListView = (ListView) findViewById(R.id.applist1);

        mAdapter = new IAAdapter(this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> av, View view, int position,
                                    long id) {
                // TODO Auto-generated method stub
                String app_name = ((TextView) view.findViewById(R.id.app_name)).getText().toString();
                String package_name = ((TextView) view.findViewById(R.id.app_package)).getText().toString();
                Toast.makeText(ioActivity.this, package_name, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ioActivity.this, ioAppResult.class);
                intent.putExtra("PACK_NAME", package_name);
                startActivity(intent);
                runCheck = 1;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        startTask();
    }

    private void startTask() {
        new AppTask().execute();
    }

    private void setLoadingView(boolean isView) {
        if (isView) {
            mLoadingContainer.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
        } else {
            mListView.setVisibility(View.VISIBLE);
            mLoadingContainer.setVisibility(View.GONE);
        }
    }

    private class ViewHolder {
        // App Icon
        public ImageView mIcon;
        // App Name
        public TextView mName;
        // App Package Name
        public TextView mPacakge;
    }

    private class IAAdapter extends BaseAdapter {
        private Context mContext = null;

        private List<ApplicationInfo> mAppList = null;
        private ArrayList<ioAppInfo> mListData = new ArrayList<ioAppInfo>();

        public IAAdapter(Context mContext) {
            super();
            this.mContext = mContext;
        }

        public int getCount() {
            return mListData.size();
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                holder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.io_app_item, null);

                holder.mIcon = (ImageView) convertView
                        .findViewById(R.id.app_icon);
                holder.mName = (TextView) convertView
                        .findViewById(R.id.app_name);
                holder.mPacakge = (TextView) convertView
                        .findViewById(R.id.app_package);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ioAppInfo data = mListData.get(position);

            if (data.mIcon != null) {
                holder.mIcon.setImageDrawable(data.mIcon);
            }

            holder.mName.setText(data.mAppName);
            holder.mPacakge.setText(data.mAppPackge);

            return convertView;
        }

        public void rebuild() {
            if (mAppList == null) {

                Log.d(TAG, "Is Empty Application List");
                pm = ioActivity.this.getPackageManager();

                //mAppList = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES | PackageManager.GET_DISABLED_COMPONENTS);
                mAppList = pm.getInstalledApplications(PackageManager.GET_META_DATA);
            }

            ioAppInfo.AppFilter filter;
            switch (MENU_MODE) {
                case MENU_DOWNLOAD:
                    filter = ioAppInfo.THIRD_PARTY_FILTER;
                    break;
                default:
                    filter = null;
                    break;
            }

            if (filter != null) {
                filter.init();
            }

            mListData.clear();

            ioAppInfo addInfo = null;
            ApplicationInfo info = null;
            for (ApplicationInfo app : mAppList) {
                info = app;

                if (filter == null || filter.filterApp(info)) {

                    addInfo = new ioAppInfo();
                    // App Icon
                    addInfo.mIcon = app.loadIcon(pm);
                    // App Name
                    addInfo.mAppName = app.loadLabel(pm).toString();
                    // App Package Name
                    addInfo.mAppPackge = app.packageName;
                    mListData.add(addInfo);
                }
            }

            Collections.sort(mListData, ioAppInfo.ALPHA_COMPARATOR);
        }
    }

    private class AppTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            setLoadingView(true);
        }

        @Override
        protected Void doInBackground(Void... params) {
            mAdapter.rebuild();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mAdapter.notifyDataSetChanged();
            setLoadingView(false);
        }

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_DOWNLOAD, 1, R.string.menu_download);
        menu.add(0, MENU_ALL, 2, R.string.menu_all);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (MENU_MODE == MENU_DOWNLOAD) {
            menu.findItem(MENU_DOWNLOAD).setVisible(false);
            menu.findItem(MENU_ALL).setVisible(true);
        } else {
            menu.findItem(MENU_DOWNLOAD).setVisible(true);
            menu.findItem(MENU_ALL).setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();

        if (menuId == MENU_DOWNLOAD) {
            MENU_MODE = MENU_DOWNLOAD;
        } else {
            MENU_MODE = MENU_ALL;
        }

        startTask();

        return true;
    }

}
