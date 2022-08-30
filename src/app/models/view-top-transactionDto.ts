import { ViewTopTransactions } from '../models/view-top-transactions';
export class ViewTopTransactionsDto {
    public topTransactions: ViewTopTransactions[];
    public statusCode: string;
    public statusMessage: string;
}