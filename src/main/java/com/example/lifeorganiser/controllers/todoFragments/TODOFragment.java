package com.example.lifeorganiser.src.controllers.todoFragments;


import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lifeorganiser.R;
import com.example.lifeorganiser.src.Models.events.TODOEvent;
import com.example.lifeorganiser.src.Models.user.UserManager;

import java.util.ArrayList;

public class TODOFragment extends Fragment {

    UserManager userManager = UserManager.getInstance();

    private TextView shortTermText;
    private TextView midTermText;
    private TextView longTermText;
    private ListView shortTermList;
    private ListView midTermList;
    private ListView longTermList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_todo, container, false);

        this.shortTermText = (TextView) root.findViewById(R.id.todoFragmentShortTermText);
        this.midTermText = (TextView) root.findViewById(R.id.todoFragmentMidTermText);
        this.longTermText = (TextView) root.findViewById(R.id.todoFragmentLongTermText);
        this.shortTermList = (ListView) root.findViewById(R.id.todoFragmentShortTermListView);
        this.midTermList = (ListView) root.findViewById(R.id.todoFragmentMidTermListView);
        this.longTermList = (ListView) root.findViewById(R.id.todoFragmentLongTermListView);

        TodoListHandler shortTermHandler = new TodoListHandler(this.shortTermText,
                this.shortTermList,
                false,
                this.userManager.getTodos(TODOEvent.Type.ShortTerm),
                TODOEvent.Type.ShortTerm);

        shortTermHandler.setListView();

        TodoListHandler midTermHandler = new TodoListHandler(this.midTermText,
                this.midTermList,
                false,
                this.userManager.getTodos(TODOEvent.Type.MidTerm),
                TODOEvent.Type.MidTerm);
        midTermHandler.setListView();

        TodoListHandler longTermHandler = new TodoListHandler(this.longTermText,
                this.longTermList,
                false,
                this.userManager.getTodos(TODOEvent.Type.LongTerm),
                TODOEvent.Type.LongTerm);
        longTermHandler.setListView();

        return root;
    }

    private class TodoListHandler{

        private TextView text;
        private ListView todoListView;
        private boolean isVisable;
        private ArrayList<TODOEvent> todos;
        private TODOEvent.Type type;

        TodoListHandler(TextView text, ListView todoListView, boolean isVisible, ArrayList<TODOEvent> todos, TODOEvent.Type type) {
            this.text= text;
            this.todoListView = todoListView;
            this.isVisable = isVisible;
            this.todos = todos;
            this.type = type;

            if (isVisible){
                this.todoListView.setVisibility(View.VISIBLE);
            }else {
                this.todoListView.setVisibility(View.GONE);
            }
        }

        void setListView(){
            final ArrayList<String> stringTodos = new ArrayList<>();

            for (TODOEvent todo : this.todos){
                stringTodos.add(todo.getTitle());
            }

            stringTodos.add(getString(R.string.add));

            ArrayAdapter adapter = new ArrayAdapter(getActivity().getApplicationContext(),
                    R.layout.simple_listview_item,
                    stringTodos);

            this.todoListView.setAdapter(adapter);

            this.text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TodoListHandler.this.isVisable) {
                        TodoListHandler.this.todoListView.setVisibility(View.GONE);
                        TodoListHandler.this.isVisable = false;
                    } else {
                        TodoListHandler.this.todoListView.setVisibility(View.VISIBLE);
                        TodoListHandler.this.isVisable = true;
                    }
                }
            });

            this.todoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    FragmentManager fm = getFragmentManager();
                    //TODO clean up logic
                    if (position == stringTodos.size() - 1) {
                        AddTODODialogFragment dialogFragment = new AddTODODialogFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("type", TodoListHandler.this.type);
                        dialogFragment.setArguments(bundle);
                        dialogFragment.show(fm, "AddTODO");
                    } else {
                        AddTODODialogFragment dialogFragment = new AddTODODialogFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("type", TodoListHandler.this.type);
                        bundle.putString("name", todos.get(position).getTitle());
                        bundle.putString("description", todos.get(position).getDescription());
                        dialogFragment.setArguments(bundle);
                        dialogFragment.show(fm, "EditTODO");
                    }
                }
            });
        }
    }

}
