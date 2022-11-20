import { User } from "./user";
import { OrgEvent } from "./orgEvent";
import { Discount } from "./discount";

export interface Volunteer_User extends User {
    name: String;
    currentPoints: number;
    level: number;
    claimedDiscounts: Discount[];
    eventsJoined: OrgEvent[];
}