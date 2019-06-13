import { Pool } from 'app/pool/pool';
import { User } from 'app/core';

export interface Reservation {
    id: number;
    from: string;
    to: string;
    count: number;
    pool: Pool;
    user: User;
}
