import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EventdetailComponent } from './eventdetail.component';

describe('EventdetailComponent', () => {
  let component: EventdetailComponent;
  let fixture: ComponentFixture<EventdetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EventdetailComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EventdetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
