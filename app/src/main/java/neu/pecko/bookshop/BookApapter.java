package neu.pecko.bookshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Pecko on 6/21/16.
 */
public class BookApapter extends BaseAdapter{ // alt + Enter

    // Explicit
    private Context context;
    private String[] iconStrings, nameStrings, priceStrings;

    public BookApapter(Context context,
                       String[] iconStrings,
                       String[] nameStrings,
                       String[] priceStrings) {
        this.context = context;
        this.iconStrings = iconStrings;
        this.nameStrings = nameStrings;
        this.priceStrings = priceStrings;
    } // Constructor


    @Override
    public int getCount() {
        return nameStrings.length; // นับ listview กี่อัน
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); // alt + Enter
        View view1 = layoutInflater.inflate(R.layout.book_listview, viewGroup, false);

        ImageView imageView = (ImageView) view1.findViewById(R.id.imageView2); // ต้องใส่ ; ก่อนแล้ว alt + Enter
        Picasso.with(context).load(iconStrings).resize(150,180).into(imageView);

        TextView nameTextView = (TextView) view1.findViewById(R.id.textView7);
        nameTextView.setText(nameStrings[i]);

        TextView priceTextView = (TextView) view1.findViewById(R.id.textView8);
        priceTextView.setText(priceStrings[i]);

        return view1;
    }
} // Main Class
