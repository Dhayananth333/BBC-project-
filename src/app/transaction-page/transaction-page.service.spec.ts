import { TestBed } from '@angular/core/testing';

import { TransactionPageService } from './transaction-page.service';

describe('TransactionPageService', () => {
  let service: TransactionPageService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TransactionPageService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
