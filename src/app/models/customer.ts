import { Address } from "./address";
import { Gender } from "./gender";
import { AccountStatus } from "./account-status";
import { AccountType } from "./account-type";
export class Customer {

    public custId: number;
    public gender: Gender;
    public name: string;
    public mobileNo: string;
    public emailId: string;
    public aadhaarNo: string;
    public panCardNo: string;
    public dateOfBirth: Date;
    public addressId: number;
    public addressLine1: string;
    public addressLine2: string;
    public landmark: string;
    public state: string;
    public city: string;
    public pincode: number;
    public accountStatus: AccountStatus;
    public accountType: AccountType;
    public balance: number;
    public incomeId: number;
    public occupationType: string;
    public incomeSource: string;
    public grossIncome: number;
    public aadharFileName: string;
    public panFileName : string;
}
