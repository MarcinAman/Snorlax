export class AdditionalTools {
    constructor(public poolId: string, public selectedTools: string[]) {
        this.poolId = poolId;
        this.selectedTools = selectedTools;
    }
}
