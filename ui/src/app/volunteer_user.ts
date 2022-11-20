import { User } from "./user";
import { OrgEvent } from "./orgEvent";
import { Discount } from "./discount";

export interface Volunteer_User extends User{
    name: String;
    currentPoints: String;
    level: Number;
    claimedDiscounts: Discount[];
    eventsJoined: OrgEvent[];
}