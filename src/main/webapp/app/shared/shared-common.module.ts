import {NgModule, LOCALE_ID} from '@angular/core';
import {Title} from '@angular/platform-browser';
import {registerLocaleData} from '@angular/common';
import locale from '@angular/common/locales/en';

import {WindowRef} from './tracker/window.service';
import {
  AccelerateWebSharedLibsModule,
  JhiLanguageHelper,
  FindLanguageFromKeyPipe,
  JhiAlertComponent,
  JhiAlertErrorComponent
} from './';

@NgModule({
  imports: [
    AccelerateWebSharedLibsModule
  ],
  declarations: [
    FindLanguageFromKeyPipe,
    JhiAlertComponent,
    JhiAlertErrorComponent
  ],
  providers: [
    JhiLanguageHelper,
    WindowRef,
    Title,
    {
      provide: LOCALE_ID,
      useValue: 'en'
    },
  ],
  exports: [
    AccelerateWebSharedLibsModule,
    FindLanguageFromKeyPipe,
    JhiAlertComponent,
    JhiAlertErrorComponent
  ]
})
export class AccelerateWebSharedCommonModule {
  constructor() {
    registerLocaleData(locale);
  }
}
