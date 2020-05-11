package com.kingja.zsqs.base;


import com.kingja.zsqs.injector.annotation.PerActivity;
import com.kingja.zsqs.injector.component.AppComponent;
import com.kingja.zsqs.injector.module.ActivityModule;
import com.kingja.zsqs.service.houses.HousesListService;
import com.kingja.zsqs.service.update.CheckUpdateService;
import com.kingja.zsqs.ui.affirm.ResultFragment;
import com.kingja.zsqs.ui.banner.BannerFragment;
import com.kingja.zsqs.ui.config.DeviceCodeConfigActivity;
import com.kingja.zsqs.ui.config.ProjectIdConfigActivity;
import com.kingja.zsqs.ui.dialog.appoint.AppointDialog;
import com.kingja.zsqs.ui.dialog.offer.OfferDialog;
import com.kingja.zsqs.ui.file.FileFragment;
import com.kingja.zsqs.ui.home.HomeFragment;
import com.kingja.zsqs.ui.housefile.HouseFileFragment;
import com.kingja.zsqs.ui.login.LoginByFaceFragment;
import com.kingja.zsqs.ui.login.LoginFragment;
import com.kingja.zsqs.ui.main.MainActivity;
import com.kingja.zsqs.ui.placement.detail.PlacementDetailFragment;
import com.kingja.zsqs.ui.placement.list.PlacementListFragment;
import com.kingja.zsqs.ui.project.ProjectDetailFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class})
public interface BaseCompnent {
    void inject(DeviceCodeConfigActivity target);

    void inject(MainActivity target);

    void inject(CheckUpdateService target);

    void inject(ProjectIdConfigActivity target);

    void inject(LoginByFaceFragment target);

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

    void inject(LoginFragment target);

    void inject(HousesListService target);
}
