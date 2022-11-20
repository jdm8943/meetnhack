import { TestBed } from '@angular/core/testing';

import { DiscountsService } from './discounts.service';

describe('DiscountsService', () => {
  let service: DiscountsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DiscountsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
