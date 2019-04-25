package id.co.maminfaruq.datasiswa.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.co.maminfaruq.datasiswa.R;
import id.co.maminfaruq.datasiswa.model.login.kelas.KelasData;
import id.co.maminfaruq.datasiswa.utils.Constants;

public class KelasAdapter extends RecyclerView.Adapter<KelasAdapter.ViewHolder> {

    // TYPE 1 untuk makanan baru
    public static final int TYPE_1 = 1;
    // TYPE 2 untuk makanan populer
    public static final int TYPE_2 = 2;
    // TYPE 3 untuk category
    public static final int TYPE_3 = 3;
    // TYPE 4 untuk makanan by category
    public static final int TYPE_4 = 4;
    // TYPE 5 untuk makanan by user
    public static final int TYPE_5 = 5;

    Integer viewType;
    private final Context context;
    private final List<KelasData> kelasDataList;



    public KelasAdapter(Integer viewType, Context context, List<KelasData> kelasDataList) {
        this.viewType = viewType;
        this.context = context;
        this.kelasDataList = kelasDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        switch (i) {
            case TYPE_1:
                view = LayoutInflater.from(context).inflate(R.layout.item_kelas, viewGroup, false);
                return new KelasNewsViewHolder(view);
            case TYPE_2:
                view = LayoutInflater.from(context).inflate(R.layout.item_kelas, viewGroup, false);
                return new KelasPopulerViewHolder(view);
            case TYPE_3:
                view = LayoutInflater.from(context).inflate(R.layout.item_row, viewGroup, false);
                return new KelasKategoriViewHolder(view);
            case TYPE_4:
                view = LayoutInflater.from(context).inflate(R.layout.item_kelas, viewGroup, false);
                return new KelasNewsViewHolder(view);
            case TYPE_5:
                view = LayoutInflater.from(context).inflate(R.layout.item_kelas, viewGroup, false);
                return new KelasByUserViewHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final KelasData kelasData = kelasDataList.get(i);

//        int mViewType = viewType;
        switch (viewType) {
            case TYPE_1:
                KelasNewsViewHolder kelasNewsViewHolder = (KelasNewsViewHolder) viewHolder;

                RequestOptions options = new RequestOptions().error(R.drawable.main).placeholder(R.drawable.main);
                Glide.with(context).load(Constants.BASE_URL + "uploads/" + kelasData.getFotoKelas()).apply(options).into(kelasNewsViewHolder.imgKelas);

                kelasNewsViewHolder.titleTv.setText(kelasData.getNamaKelas());
                kelasNewsViewHolder.txtView.setText(kelasData.getView());
                kelasNewsViewHolder.tvKategori.setText(kelasData.getNamaKategori());

                // Menampilkan waktu upload
                kelasNewsViewHolder.insertTime.setText(newDate(kelasData.getInsertTime()));

                kelasNewsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Kamu memilih " + kelasData.getNamaKelas(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case TYPE_2:
                KelasPopulerViewHolder kelasPopulerViewHolder = (KelasPopulerViewHolder) viewHolder;

                // Requestoption untuk error dan placeholder gambar
                RequestOptions options2 = new RequestOptions().error(R.drawable.main).placeholder(R.drawable.main);
                Glide.with(context).load(Constants.BASE_URL + "uploads/" + kelasData.getFotoKelas()).apply(options2).into(kelasPopulerViewHolder.imgKelas);
                // Menampilkan tittle dan jumlah view
                kelasPopulerViewHolder.titleTv.setText(kelasData.getNamaKelas());
                kelasPopulerViewHolder.txtView.setText(kelasData.getView());

                kelasPopulerViewHolder.tvKategori.setText(kelasData.getNamaKategori());

                // Menampilkan waktu upload
                kelasPopulerViewHolder.insertTime.setText(newDate(kelasData.getInsertTime()));

                kelasPopulerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Kamu memilih " + kelasData.getNamaKelas(), Toast.LENGTH_SHORT).show();

                    }
                });
                break;
            case TYPE_3:
                KelasKategoriViewHolder kelasKategoriViewHolder = (KelasKategoriViewHolder) viewHolder;

                RequestOptions options3 = new RequestOptions().error(R.drawable.main).placeholder(R.drawable.main);
                Glide.with(context).load(Constants.BASE_URL + "image/" + kelasData.getFotoKategori()).apply(options3).into(kelasKategoriViewHolder.image);

                kelasKategoriViewHolder.txtNamaKategory.setText(kelasData.getNamaKategori());

                kelasKategoriViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Kamu memilih " + kelasData.getNamaKategori(), Toast.LENGTH_SHORT).show();

                    }
                });
                break;
            case TYPE_4:
                KelasNewsViewHolder kelasNewsViewHolder2 = (KelasNewsViewHolder) viewHolder;

                RequestOptions options4 = new RequestOptions().error(R.drawable.main).placeholder(R.drawable.main);
                Glide.with(context).load(kelasData.getUrlKelas()).apply(options4).into(kelasNewsViewHolder2.imgKelas);

                kelasNewsViewHolder2.titleTv.setText(kelasData.getNamaKelas());
                kelasNewsViewHolder2.txtView.setText(kelasData.getView());
                kelasNewsViewHolder2.tvKategori.setText(kelasData.getNamaKategori());

                kelasNewsViewHolder2.insertTime.setText(kelasData.getInsertTime());

                kelasNewsViewHolder2.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Kamu memilih " + kelasData.getNamaKelas(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case TYPE_5:
                KelasByUserViewHolder kelasByUserViewHolder = (KelasByUserViewHolder) viewHolder;

                // Requestoption untuk error dan placeholder gambar
                RequestOptions options5 = new RequestOptions().error(R.drawable.main).placeholder(R.drawable.main);
                Glide.with(context).load(kelasData.getUrlKelas()).apply(options5).into(kelasByUserViewHolder.imgKelas);

                // Menampilkan tittle dan jumlah view
                kelasByUserViewHolder.titleTv.setText(kelasData.getNamaKelas());
                kelasByUserViewHolder.txtView.setText(kelasData.getView());

                kelasByUserViewHolder.tvKategori.setText(kelasData.getNamaKategori());

                // Menampilkan waktu upload
                kelasByUserViewHolder.insertTime.setText(newDate(kelasData.getInsertTime()));

                kelasByUserViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Kamu memilih " + kelasData.getNamaKelas(), Toast.LENGTH_SHORT).show();

                    }
                });
                break;


        }

    }

    private String newDate(String insertTime) {
        // Membuat variable penampung tanggal
        Date date = null;
        // Membuat penampung date untuk format yang baru
        String newDate = insertTime;

        // Membuat date dengan format sesuai dengan tanggal yang sudah dimiliki
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // mengubah tanggal yang dimiliki menjadi tipe date
        try {
            date = sdf.parse(insertTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Kita cek format date yang kita miliki sesuai dengan yang kita inginkan
        if (date != null) {
            // Mengubah date yang dimiliki menjadi format date yang baru
            newDate = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss").format(date);
        }
        return newDate;
    }

    @Override
    public int getItemCount() {
        if (kelasDataList == null) return 0;
        return kelasDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return viewType;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class KelasNewsViewHolder extends ViewHolder {

        @BindView(R.id.img_kelas)
        AppCompatImageView imgKelas;
        @BindView(R.id.titleTv)
        TextView titleTv;
        @BindView(R.id.insertTime)
        TextView insertTime;
        @BindView(R.id.txt_view)
        TextView txtView;
        @BindView(R.id.tv_kategori)
        TextView tvKategori;


        public KelasNewsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class KelasPopulerViewHolder extends ViewHolder {

        @BindView(R.id.img_kelas)
        AppCompatImageView imgKelas;
        @BindView(R.id.titleTv)
        TextView titleTv;
        @BindView(R.id.insertTime)
        TextView insertTime;
        @BindView(R.id.txt_view)
        TextView txtView;
        @BindView(R.id.tv_kategori)
        TextView tvKategori;

        public KelasPopulerViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class KelasKategoriViewHolder extends ViewHolder {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.txt_nama_kategory)
        TextView txtNamaKategory;

        public KelasKategoriViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class KelasByUserViewHolder extends ViewHolder {

        @BindView(R.id.img_kelas)
        AppCompatImageView imgKelas;
        @BindView(R.id.titleTv)
        TextView titleTv;
        @BindView(R.id.insertTime)
        TextView insertTime;
        @BindView(R.id.txt_view)
        TextView txtView;
        @BindView(R.id.tv_kategori)
        TextView tvKategori;


        public KelasByUserViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
