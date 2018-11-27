package com.example.cs160_sp18.prog3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Date;

// Displays a list of comments for a particular landmark.
public class CommentFeedActivity extends AppCompatActivity {

    private static final String TAG = CommentFeedActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private String username;

    private ArrayList<String> mUsernames = new ArrayList<>();
    private ArrayList<Comment> mComments = new ArrayList<>();


    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference usersReference = database.getReference();


    // UI elements
  ;
    private Button but;
    private Toolbar toolbar;
    private EditText comments;
    private RelativeLayout layout;
    private TextView userview;
    private TextView commentview;
    private TextView dateview;

    /* TODO: right now mRecyclerView is using hard coded comments.
     * You'll need to add functionality for pulling and posting comments from Firebase
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_feed);

        userview = findViewById(R.id.username_text_view);
        commentview = findViewById(R.id.comment_text_view);
        dateview = findViewById(R.id.date_text_view);


        // TODO: replace this with the name of the landmark the user chose
        String landmarkName = "test landmark";

        // sets the app bar's title
        setTitle(landmarkName + ": Posts");

        // hook up UI elements
        layout =  findViewById(R.id.comment_layout);
        comments = layout.findViewById(R.id.comment_input_edit_text);
        but = layout.findViewById(R.id.send_button);


        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationIcon(R.drawable.icons8);
        Intent goToSecondActivityIntent = getIntent();
        Bundle intentExtras = goToSecondActivityIntent.getExtras();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent();
                newIntent.putExtra("username", username);
                setResult(RESULT_OK, newIntent);
                //  goToSecondActivityIntent.putExtra("username", (String) intentUsernameExtra.get("username"));
               // startActivity(new Intent(getApplicationContext(), recyclerview.class));
                finish();
            }
        });


        if (intentExtras != null) {
            landmarkName = (String) intentExtras.get("landmarkname_now");
            toolbar.setTitle(landmarkName + ": Posts");
        }


        username = (String) intentExtras.get("username");



        mRecyclerView = (RecyclerView) findViewById(R.id.comment_recycler);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // create an onclick for the send button
        setOnClickForSendButton();

        // make some test comment objects that we add to the recycler view
        makeTestComments();

        // use the comments in mComments to create an adapter. This will populate mRecyclerView
        // with a custom cell (with comment_cell_layout) for each comment in mComments
        setAdapterAndUpdateData();

/*
//https://firebase.google.com/docs/database/android/read-and-write//
*/

        DatabaseReference currentPic = usersReference.child(landmarkName);

        currentPic.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    Comment newComment = postSnapShot.getValue(Comment.class);
                    writeNewPost(newComment.username, newComment.text,  newComment.date);
                    postNewComment(newComment.username);
                }
                setEditingEnabled(true);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });




    }

    // TODO: delete me
    private void makeTestComments() {
        String randomString = "hello world hello world ";
        Comment newComment = new Comment(randomString, "test_user1", new Date());
        Comment hourAgoComment = new Comment(randomString + randomString, "test_user2", new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
        Comment overHourComment = new Comment(randomString, "test_user3", new Date(System.currentTimeMillis() - (2 * 60 * 60 * 1000)));
        Comment dayAgoComment = new Comment(randomString, "test_user4", new Date(System.currentTimeMillis() - (25 * 60 * 60 * 1000)));
        Comment daysAgoComment = new Comment(randomString + randomString + randomString, "test_user5", new Date(System.currentTimeMillis() - (48 * 60 * 60 * 1000)));
        mComments.add(newComment);
        mComments.add(hourAgoComment);
        mComments.add(overHourComment);
        mComments.add(dayAgoComment);
        mComments.add(daysAgoComment);


    }

    private void setOnClickForSendButton() {
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = comments.getText().toString();

                if (TextUtils.isEmpty(comment)) {
                    comments.requestFocus();
                } else {
                    Comment newComment = new Comment(username, comment, new Date());
                    writeNewPost(comment, username, new Date());

                    usersReference.push().setValue(newComment);
                    postNewComment(comment);
                }
            }
        });
    }

    private void setAdapterAndUpdateData() {

        mAdapter = new CommentAdapter(this, mComments);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.smoothScrollToPosition(mComments.size() - 1);
    }



    private void postNewComment(String commentText) {
        Comment newComment = new Comment(commentText, username, new Date());
        mComments.add(newComment);
        setAdapterAndUpdateData();
        comments.setText("");
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
//https://firebase.google.com/docs/database/android/lists-of-data//
    private void writeNewPost(String text, String username, Date date) {

        String texting = comments.getText().toString();
        Comment newComment = new Comment(texting, username, new Date());

        usersReference.child("comment" + Integer.toString(mComments.size())).setValue(newComment);
        setAdapterAndUpdateData();


    }


    private void setEditingEnabled(boolean enabled) {
        comments.setEnabled(enabled);

        if (enabled) {
            but.setVisibility(View.VISIBLE);
        } else {
            but.setVisibility(View.GONE);
        }

    }

}

