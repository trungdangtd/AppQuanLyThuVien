package edu.huflit.myapplication4.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

import edu.huflit.myapplication4.Adapter.SliderAdapter;
import edu.huflit.myapplication4.Entity.SliderData;
import edu.huflit.myapplication4.MainActivity;
import edu.huflit.myapplication4.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomePageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomePageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomePageFragment newInstance(String param1, String param2) {
        HomePageFragment fragment = new HomePageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_page, container, false);
    }
    SliderView sliderView;
    TextView watchMoreBtn;
    ImageView genreListBtn;
    AutoCompleteTextView searchBar_ACTV;
    RecyclerView bookList;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GetIDPalletes(view);
        SetPalletes(view);
    }
    // Gọi các pallete có trong layout
    void GetIDPalletes(View view)
    {
        sliderView = view.findViewById(R.id.slider);
        genreListBtn = view.findViewById(R.id.GenreListBtn);
    }

    // Gán chức năng cho các pallete
    void SetPalletes(View view)
    {
        AdsSlider();
    }
    void AdsSlider()
    {
        // Link dẫn đến hình ảnh trên website
        String url1 = "https://toquoc.mediacdn.vn/Uploaded/hangdd/2018_06_15/35329628_1688473887927396_4677348848543203328_n_BJQJ.jpg";
        String url2 = "https://thaihabooks.com/wp-content/uploads/2019/08/chat-beo-4.jpg";
        String url3 = "https://static.ybox.vn/2018/3/19/20f0ef46-2b71-11e8-bdf8-2e995a9a3302.png";

        // Tạo danh sách mảng để lưu trữ các url hình ảnh của mình.
        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();

        // Thêm các url trong danh sách mảng
        sliderDataArrayList.add(new SliderData(url1));
        sliderDataArrayList.add(new SliderData(url2));
        sliderDataArrayList.add(new SliderData(url3));

        // Chuyển danh sách mảng này vào bên trong lớp bộ điều hợp của chúng tôi.
        SliderAdapter adapter = new SliderAdapter(MainActivity.instance, sliderDataArrayList);

        // Phương thức bên dưới được sử dụng để đặt hướng chu trình tự động ở bên trái thành đúng hướng bạn có thể thay đổi theo yêu cầu.
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

        // Phương thức dưới đây được sử dụng để  setadapter thành chế độ xem trượt.
        sliderView.setSliderAdapter(adapter);

        // Phương thức bên dưới được sử dụng để thiết lập thời gian cuộn tính bằng giây.
        sliderView.setScrollTimeInSec(3);

        // Để thiết lập nó có thể cuộn tự động sử dụng phương pháp dưới đây.
        sliderView.setAutoCycle(true);

        // Để khởi động autocycle, phương thức dưới đây được sử dụng.
        sliderView.startAutoCycle();
    }
}