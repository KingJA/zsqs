package com.kingja.zsqs.base;


import com.kingja.zsqs.injector.annotation.PerActivity;
import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.injector.module.ActivityModule;
import com.kingja.zsqs.ui.affirm.ResultFragment;
import com.kingja.zsqs.ui.banner.BannerFragment;
import com.kingja.zsqs.ui.dialog.appoint.AppointDialog;
import com.kingja.zsqs.ui.dialog.offer.OfferDialog;
import com.kingja.zsqs.ui.file.FileFragment;
import com.kingja.zsqs.ui.home.HomeFragment;
import com.kingja.zsqs.ui.housefile.HouseFileFragment;
import com.kingja.zsqs.ui.placement.detail.PlacementDetailFragment;
import com.kingja.zsqs.ui.placement.list.PlacementListFragment;
import com.kingja.zsqs.ui.project.ProjectDetailFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class})
public interface BaseCompnent {
    void inject(ProjectDetailFragment target);

    void inject(FileFragment target);

    void inject(ResultFragment target);

    void inject(PlacementListFragment target);

    void inject(PlacementDetailFragment target);

    void inject(OfferDialog target);

    void inject(AppointDialog target);

    void inject(BannerFragment target);

    void inject(HomeFragment target);

    void inject(HouseFileFragment target);
}
