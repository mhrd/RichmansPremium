package android.mehrdad.richmanspremium;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

public class TutorialRecyclerAdapter extends RecyclerView.Adapter<TutorialViewHolder> {

    private List<Tutorial> tutorials;
    Context context;

    public TutorialRecyclerAdapter(Context context, List<Tutorial> tutorials) {
        this.tutorials = tutorials;
        this.context = context;
    }

    View view;

    @Override
    public TutorialViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tutorials, parent, false);
        return new TutorialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TutorialViewHolder holder, int position) {
        final Tutorial tutorial = tutorials.get(position);

        holder.txtName.setText(tutorial.name);
        holder.txtExplain.setText(tutorial.explain);
        Glide.with(context).load(tutorial.image).into(holder.imgTutorial);
        holder.type = tutorial.type;
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tutorial.plan.equals("p") && !HomePageActivity.plan.equals("p")) {
                    Intent i = new Intent(context, PurchaseActivity.class);
                    context.startActivity(i);

//                    Toast.makeText(view.getContext(), "برای استفاده از این آموزش نسخه اصلی برنامه را تهیه کنید", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    Intent i = new Intent(context, TutorialActivity.class);
                    i.putExtra("id", tutorial.id);
                    i.putExtra("name", tutorial.name);
                    i.putExtra("type", tutorial.type);
                    if (tutorial.type == 1) {
                        i.putExtra("url", tutorial.video);
                    }
                    if (tutorial.type == 2) {
                        i.putExtra("url", tutorial.sound);
                    }
                    if (tutorial.type == 3) {
                        i.putExtra("url", tutorial.text);
                    }
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tutorials.size();
    }
}
