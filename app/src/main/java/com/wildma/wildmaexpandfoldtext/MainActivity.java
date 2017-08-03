package com.wildma.wildmaexpandfoldtext;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author     wildma
 * DATE       2017/8/3
 * Des	      ${TODO}
 */
public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    List<ExpandFoldTextBean> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        ExpandFoldTextAdapter adapter = new ExpandFoldTextAdapter(mList, this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(adapter);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        String longContent = "-->游泳、快走、慢跑、骑自行车，及一切有氧运动都能锻炼心脏。有氧运动好处多：能锻炼心肺、增强循环系统功能、燃烧脂肪、加大肺活量、降低血压，甚至能预防糖尿病，减少心脏病的发生。美国运动医学院建议，想知道有氧运动强度是否合适，可在运动后测试心率，以达到最高心率的60%—90%为宜。如果想通过有氧运动来减肥，可以选择低度到中度的运动强度，同时延长运动时间，这种方法消耗的热量更多。运动频率每周3—5次，每次20—60分钟。想要锻炼肌肉，可以练举重、做体操以及其他重复伸、屈肌肉的运动。肌肉锻炼可以燃烧热量、增强骨密度、减少受伤，尤其是关节受伤的几率，还能预防骨质疏松。 在做举重运动前，先测一下，如果连续举8次你最多能举多重的东西，就从这个重量开始练习。当你可以连续12次举起这个重量时，试试增加5%的重量。注意每次练习时，要连续举8—12次，这样可以达到肌肉最大耐力的70%—80%，锻炼效果较好。每周2—3次，但要避免连续两天锻炼同一组肌肉群， 以便让肌肉有充分的恢复时间。";
        String shortContent = "-->健身是一种体育项目，如各种徒手健美操、韵律操、形体操以及各种自抗力动作。";
        for (int i = 0; i < 20; i++) {
            ExpandFoldTextBean bean = new ExpandFoldTextBean();
            if (i % 2 == 0) {
                bean.setContent(i + shortContent);
                bean.setId(i);
            } else {
                bean.setContent(i + longContent);
                bean.setId(i);
            }
            mList.add(bean);
        }
    }
}
