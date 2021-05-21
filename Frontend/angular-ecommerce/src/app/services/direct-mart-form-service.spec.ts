import { TestBed } from '@angular/core/testing';

import { DirectMartFormService } from './direct-mart-form-service';

describe('Lu2ShopFormServiceService', () => {
  let service: DirectMartFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DirectMartFormService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
