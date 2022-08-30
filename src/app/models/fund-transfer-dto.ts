export class FundTransferDto {
    fromAccount: string;
    toAccount: string;
    amount: number;
    transactionType: string;
    password: string;
    otp: string;
}