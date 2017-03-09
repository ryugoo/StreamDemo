package com.r384ta.android.streamdemo.viewmodel;

import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;

import com.annimon.stream.Optional;
import com.google.gson.Gson;
import com.r384ta.android.streamdemo.di.module.ProvideNames;
import com.r384ta.android.streamdemo.dto.HttpBinGetDto;
import com.r384ta.android.streamdemo.dto.HttpBinGetDtoSpotRepository;
import com.r384ta.android.streamdemo.misc.AndroidDisposable;
import com.r384ta.android.streamdemo.model.HttpBinGet;
import com.r384ta.android.streamdemo.network.HttpBinService;
import com.r384ta.android.streamdemo.type.Either;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import retrofit2.Response;

public class RxJavaViewModel extends BaseViewModel {
    private static final String TAG = RxJavaViewModel.class.getSimpleName();

    private final BehaviorSubject<Spanned> mHttpBinText = BehaviorSubject.create();

    @Inject
    @Named(ProvideNames.APPLICATION_CONTEXT)
    Context mContext;

    @Inject
    HttpBinService mHttpBinService;

    @Inject
    Gson mGson;

    @Inject
    AndroidDisposable mDisposable;

    public RxJavaViewModel(Fragment fragment) {
        super(fragment);
        getViewModelComponent().inject(this);

        // Emit initial value
        Optional.of(HttpBinGetDtoSpotRepository.getEntity(mContext))
            .flatMap(dao -> Optional.ofNullable(dao.httpBinGet))
            .map(this::toHttpBinText)
            .filter(text -> !TextUtils.isEmpty(text))
            .ifPresent(mHttpBinText::onNext);
    }

    //region Public
    @Override
    public void dispose() {
        mDisposable.dispose();
        super.dispose();
    }

    public void httpBinTextRequest() {
        mDisposable.add(mHttpBinService.get()
            .map(Response::body)
            .doOnNext(this::saveToCache)
            .map(this::toHttpBinText)
            .filter(text -> !TextUtils.isEmpty(text))
            .subscribe(mHttpBinText::onNext, mHttpBinText::onError));
    }

    public Observable<Either<Throwable, Spanned>> httpBinText() {
        return mHttpBinText
            .map(Either::<Throwable, Spanned>right)
            .onErrorReturn(Either::left);
    }
    //endregion

    //region Private
    @SuppressWarnings("deprecation")
    private Spanned toHttpBinText(HttpBinGet httpBinGet) {
        final String source = new StringBuilder("<b>HttpBinGet#toString</b><br>")
            .append(httpBinGet.toString())
            .append("<br><br><b>Gson#toJson</b><br>")
            .append(mGson.toJson(httpBinGet)
                .replaceAll("\\n", "<br>")
                .replaceAll("\\s", "&ensp;"))
            .toString();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(source);
        }
    }

    private void saveToCache(HttpBinGet httpBinGet) {
        HttpBinGetDtoSpotRepository.putEntity(mContext, new HttpBinGetDto(httpBinGet));
    }
    //endregion
}
