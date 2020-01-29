package com.marcinadd.projecty;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.marcinadd.projecty.listener.UserNotLoggedListener;
import com.marcinadd.projecty.login.LoginService;
import com.marcinadd.projecty.message.model.Message;
import com.marcinadd.projecty.model.Role;
import com.marcinadd.projecty.project.model.ProjectRole;
import com.marcinadd.projecty.service.ChatService;
import com.marcinadd.projecty.task.model.Task;
import com.marcinadd.projecty.team.model.TeamRole;
import com.marcinadd.projecty.ui.login.LoginActivity;
import com.marcinadd.projecty.ui.message.MessageListFragment;
import com.marcinadd.projecty.ui.project.ProjectListFragment;
import com.marcinadd.projecty.ui.project.manage.ProjectRoleFragment;
import com.marcinadd.projecty.ui.task.TaskFragment;
import com.marcinadd.projecty.ui.team.TeamListFragment;
import com.marcinadd.projecty.user.UserService;

public class Main2Activity extends AppCompatActivity implements ProjectListFragment.OnListFragmentInteractionListener,
        ProjectRoleFragment.OnListFragmentInteractionListener,
        TaskFragment.OnListFragmentInteractionListener,
        MessageListFragment.OnListFragmentInteractionListener,
        TeamListFragment.OnListFragmentInteractionListener,
        UserNotLoggedListener {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        startService(new Intent(this, ChatService.class));

        if (!getCurrentUserUsername().isEmpty()) {
            LoginService.getInstance(getApplicationContext()).checkIfUserIsLogged(this);
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            NavigationView navigationView = findViewById(R.id.nav_view);
            UserService.setSidebarData(navigationView.getHeaderView(0), getApplicationContext());

            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_project, R.id.nav_message_received, R.id.nav_message_sent, R.id.nav_team)
                    .setDrawerLayout(drawer)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);
        } else {
            onUserNotLogged();
        }
    }

    public String getCurrentUserUsername() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return sharedPreferences.getString("username", "");
    }

    @Override
    public void onUserNotLogged() {
        Intent intent = new Intent(this, LoginActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onListFragmentInteraction(Task item) {

    }

    @Override
    public void onListFragmentInteraction(Message message) {

    }

    @Override
    public void onListFragmentInteraction(TeamRole item) {

    }

    @Override
    public void onListFragmentInteraction(Role item) {

    }

    @Override
    public void onListFragmentInteraction(ProjectRole item) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                UserService.logOut(getApplicationContext());
                onUserNotLogged();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
