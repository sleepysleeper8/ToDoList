package com.example.todolist;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import java.util.List;

import static com.example.todolist.EditActivity.EXTRA_TASK;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    SortedList<Task> data;


    public MyAdapter() {

        data = new SortedList<>(Task.class, new SortedList.Callback<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                if (!o2.done && o1.done) {
                    return 1;
                }
                if (o2.done && !o1.done) {
                    return -1;
                }
                return (int) (o2.time - o1.time);
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(Task oldItem, Task newItem) {
                return false;
            }

            @Override
            public boolean areItemsTheSame(Task item1, Task item2) {
                return false;
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }
        });
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setItems(List<Task> tasks) {
        data.replaceAll(tasks);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        CheckBox completed;
        View delete;
        Task task;
        boolean updateTasks;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.textTask);
            completed = itemView.findViewById(R.id.checkBoxCompleted);
            delete = itemView.findViewById(R.id.buttonDelete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent;
                    intent = new Intent(itemView.getContext(), EditActivity.class);
                    if (task != null) {
                        intent.putExtra(EXTRA_TASK, task);
                    }
                    itemView.getContext().startActivity(intent);
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    App.getInstance().getTasksDao().delete(task);
                }
            });

            completed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (!updateTasks) {
                        task.done = isChecked;
                        App.getInstance().getTasksDao().update(task);
                    }
                }
            });
        }

        public void bind(Task task) {
            this.task = task;
            text.setText(task.text);
            updateTasks = true;
            completed.setChecked(task.done);
            updateTasks = false;
        }
    }
}
