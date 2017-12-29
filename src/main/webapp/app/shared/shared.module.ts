import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {DatePipe} from '@angular/common';

import {
  AccelerateWebSharedLibsModule,
  AccelerateWebSharedCommonModule,
  CSRFService,
  AuthServerProvider,
  AccountService,
  UserService,
  StateStorageService,
  LoginService,
  LoginModalService,
  JhiLoginModalComponent,
  Principal,
  JhiTrackerService,
  HasAnyAuthorityDirective,
  JhiSocialComponent,
  SocialService,
} from './';

@NgModule({
  imports: [
    AccelerateWebSharedLibsModule,
    AccelerateWebSharedCommonModule
  ],
  declarations: [
    JhiSocialComponent,
    JhiLoginModalComponent,
    HasAnyAuthorityDirective
  ],
  providers: [
    LoginService,
    LoginModalService,
    AccountService,
    StateStorageService,
    Principal,
    CSRFService,
    JhiTrackerService,
    AuthServerProvider,
    SocialService,
    UserService,
    DatePipe
  ],
  entryComponents: [JhiLoginModalComponent],
  exports: [
    AccelerateWebSharedCommonModule,
    JhiSocialComponent,
    JhiLoginModalComponent,
    HasAnyAuthorityDirective,
    DatePipe
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]

})
export class AccelerateWebSharedModule {}
