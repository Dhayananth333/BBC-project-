import { TestBed } from '@angular/core/testing';

import { BillPageService } from './bill-page.service';

describe('BillPageService', () => {
  let service: BillPageService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BillPageService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
