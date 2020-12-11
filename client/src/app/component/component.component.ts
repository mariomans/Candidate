import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';
@Component({
  selector: 'app-component',
  templateUrl: './component.component.html',
  styleUrls: ['./component.component.css']
})
export class ComponentComponent implements OnInit {
  users: Array<any>;

  constructor(private userService: UserService) { }

  ngOnInit() {
    this.userService.getAll().subscribe((data) => {
      this.users = data;
      console.log(this.users)
    })
  }

}
