package pvsasoftware.mathgames.Multiplayer.FriendList;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.ArrayList;
import java.util.List;

import pvsasoftware.mathgames.MainHaindler;
import pvsasoftware.mathgames.Model.DataManager;
import pvsasoftware.mathgames.Model.GameType3;
import pvsasoftware.mathgames.Multiplayer.AddFriend.Fragment_AddFriend;
import pvsasoftware.mathgames.Multiplayer.PlayOnline.ConnectedPlayerDialog;
import pvsasoftware.mathgames.Multiplayer.SimpleDividerItemDecoration;
import pvsasoftware.mathgames.Multiplayer.enties.GamePlayDataOnline;
import pvsasoftware.mathgames.Multiplayer.enties.GameTypeOnline1;
import pvsasoftware.mathgames.Multiplayer.enties.GameTypeOnline2;
import pvsasoftware.mathgames.Multiplayer.enties.Multiplayer;
import pvsasoftware.mathgames.PlayMath1Activity;
import pvsasoftware.mathgames.R;

/**
 * Created by thien on 12/21/2016.
 */

public class MultiplayerActivity extends AppCompatActivity implements MultiplayerActivityInterface,OnItemClickListener {
    private RecyclerView recyclerView;
    private MultiplayerAdapter multiplayerAdapter;
    private Toolbar toolbar;
    private MultiplayerHandler multiplayerHandler;
    private FloatingActionButton fab,randomfab;
    public ConnectedPlayerDialog frag;
    private GamePlayDataOnline gamePlayDataOnline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);
        multiplayerHandler = new MultiplayerHandler(this);
        init();
        toolbar.setSubtitle(multiplayerHandler.getCurrentUserEmail());
        setSupportActionBar(toolbar);
        creatDataToSend();

    }

    private void init() {
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewContacts);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        multiplayerAdapter = new MultiplayerAdapter(new ArrayList<Multiplayer>(),this );
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(multiplayerAdapter);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        fab = (FloatingActionButton)findViewById(R.id.fab);
        randomfab = (FloatingActionButton)findViewById(R.id.fakePlay);
        randomfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRandomPlay();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    showAddFriend();
            }
        });
    }

    private void creatDataToSend() {
        DataManager dataManager = new DataManager(this);
        List<GameTypeOnline1> gameTypeOnline1s = dataManager.creatGameTypeOnline1();
        List<GameTypeOnline2> gameTypeOnline2s = dataManager.creatGameTypeOnline2();
        List<GameType3> gameType3s = dataManager.createGameType3list();
        gamePlayDataOnline = new GamePlayDataOnline(gameTypeOnline1s,gameTypeOnline2s,gameType3s);
        multiplayerHandler.initSetData(gamePlayDataOnline);
    }

    private void showAddFriend() {
        final Fragment_AddFriend frag = new Fragment_AddFriend();
        frag.show(getSupportFragmentManager(),"");

    }

    public void connectPlayer() {
        MainHaindler.getInstance().setShowDialog(true);
        frag = new ConnectedPlayerDialog();
        frag.show(getSupportFragmentManager(), "");
    }
    public void dismisDiaLogConnect(){
        if (frag != null ) {
            frag.dismiss();
        }
    }



    @Override
    protected void onResume() {
        super.onResume();

        multiplayerHandler.onResume();
    }

    @Override
    protected void onDestroy() {
        multiplayerHandler.onDestroy();
        disConnectGoogleAPI();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        multiplayerHandler.onPause();
        disConnectGoogleAPI();
        super.onPause();
    }

    @Override
    protected void onStop() {
        disConnectGoogleAPI();
        super.onStop();
    }

    @Override
    public void onContactAdded(Multiplayer user) {
        multiplayerAdapter.add(user);
    }

    @Override
    public void onContactChanged(Multiplayer user) {
            multiplayerAdapter.update(user);
    }

    @Override
    public void onContactRemoved(Multiplayer user) {
            multiplayerAdapter.remove(user);
    }

    @Override
    public void onPlayerConnected(final String email) {
        if (!MainHaindler.getInstance().isShowDialog() && !MainHaindler.getInstance().isConnectedPlayer()) {
            MainHaindler.getInstance().setShowDialog(true);
            new AlertDialog.Builder(this).setTitle(getString(R.string.playerConnected))
                    .setMessage(String.format(getString(R.string.choosePlayer), email))

                    .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MainHaindler.getInstance().setShowDialog(false);
                            multiplayerHandler.acceptToPlay(email);
                           // multiplayerHandler.setCancelPlayerConnected(email);
                        }
                    })
                    .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MainHaindler.getInstance().setShowDialog(false);
                            multiplayerHandler.setCancelPlayerConnected(email);

                        }
                    }).show().setCanceledOnTouchOutside(false);
        }
    }

    @Override
    public void onItemClick(Multiplayer user) {
        MainHaindler.getInstance().setGameTypeOnline1s(gamePlayDataOnline.gameTypeOnline1s);
        MainHaindler.getInstance().setGameTypeOnline2s(gamePlayDataOnline.gameTypeOnline2s);
        MainHaindler.getInstance().setGameType3s(gamePlayDataOnline.gameType3s);
                multiplayerHandler.invitePlayer(user);

    }


    private void setRandomPlay() {
        frag = new ConnectedPlayerDialog();
        frag.show(getSupportFragmentManager(), "");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismisDiaLogConnect();
                MainHaindler.getInstance().setFakeUser(true);
                startGame();

            }
        },500);
    }

    public void disConnectToPlayer( ) {
        multiplayerHandler.disConnectToPlayer();
    }


    @Override
    public void cantConnecctPlayer() {
        Toast.makeText(this, R.string.cant_connect,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemLongClick(Multiplayer user) {
        final String email = user.getMultiplayerEmail();
        new AlertDialog.Builder(this).setTitle(getString(R.string.delete))
                .setTitle(getString(R.string.delete))
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (email != null) {
                            multiplayerHandler.removeContact(email);
                        }

                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_friend_list,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_logout) {
            googleSignOut();
        }
        return true;
    }

    private void disConnectGoogleAPI() {
        if (MainHaindler.getInstance().getClient() != null) {
            GoogleApiClient mGoogleApiClient = MainHaindler.getInstance().getClient();
            mGoogleApiClient.disconnect();
        }
    }

    private void googleSignOut() {
        multiplayerHandler.signOff();
        if (MainHaindler.getInstance().getClient() != null) {
            final GoogleApiClient mGoogleApiClient = MainHaindler.getInstance().getClient();
            mGoogleApiClient.connect();
            mGoogleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                @Override
                public void onConnected(@Nullable Bundle bundle) {

                    if (mGoogleApiClient.isConnected()) {
                        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                            @Override
                            public void onResult(@NonNull Status status) {

                                mGoogleApiClient.disconnect();
                            }
                        });
                    }

                }

                @Override
                public void onConnectionSuspended(int i) {

                }
            });
        }
        finish();
    }
    public void startGame(){
        if (!MainHaindler.getInstance().isConnectedPlayer() ) {
            MainHaindler.getInstance().setConnectedPlayer(true);
            Intent intent = new Intent(this,PlayMath1Activity.class);
            startActivity(intent);
        }

    }


}


