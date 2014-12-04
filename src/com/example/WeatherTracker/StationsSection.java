package com.example.WeatherTracker;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Created by anoopc on 12/1/14.
 */
public class StationsSection extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private SimpleCursorAdapter stationListCursorAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stations_section, container, false);
        populateView(view);
        return view;
    }

    private void populateView(View view) {
        final ListView listView = (ListView)view.findViewById(R.id.stations_list);
        if (listView == null) {
            return;
        }
        if(this.stationListCursorAdapter == null) {
            this.stationListCursorAdapter = new SimpleCursorAdapter(
                    getActivity(),
                    R.layout.station_list_item,
                    null,
                    new String[]{
                        StationTableDBHelper.StationTable.STATION_CODE,
                        StationTableDBHelper.StationTable.STATION_FULL_NAME},
                    new int[]{R.id.item_heading, R.id.item_subheading},
                    0);
        }

        Log.d("ANOOPC", "set Adapter + " + this.stationListCursorAdapter);
        listView.setAdapter(this.stationListCursorAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("ANOOPC_list1", "" + parent + " " + view + " " + Integer.toString(position) + " " + Long.toString(id));
            }
        });

        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.stations_list) {
            final long stationRowID = ((AdapterView.AdapterContextMenuInfo)menuInfo).id;
            menu.add(R.string.favorite_station).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    ((MyActivity)getActivity()).onFavoriteStation(stationRowID);
                    return true;
                }
            });

            menu.add(R.string.delete_station).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    ((MyActivity)getActivity()).onDeleteStation(stationRowID);
                    return true;
                }
            });
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
        Log.d("ANOOPC", "Why Why Why");
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //ignore id(only one loader being created)
        Uri stationDataURI = StationTableDBHelper.StationTable.CONTENT_URI;
        Log.d("ANOOPC", "onCreateLoader" + stationDataURI);
        return new CursorLoader(
                getActivity(),
                stationDataURI,
                new String[]{
                        StationTableDBHelper.StationTable._ID,
                        StationTableDBHelper.StationTable.STATION_CODE,
                        StationTableDBHelper.StationTable.STATION_FULL_NAME},
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d("ANOOPC", "onLoadFinished");
        this.stationListCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d("ANOOPC", "onLoaderReset");
        this.stationListCursorAdapter.swapCursor(null);
    }
}
