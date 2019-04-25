import { Tool } from 'app/pool/tool';

export interface Pool {
    poolId?: string;
    displayName: string;
    maximumCount: number;
    enabled: boolean;
    description: Tool[];
}
