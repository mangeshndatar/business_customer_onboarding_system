export interface BusinessApplication {
  id?: number;
  legalName: string;
  legalStructure: string;
  countryOfIncorporation: string;
  registrationNumber: string;
  taxId?: string;
  industry: string;
  estimatedTurnover?: number;
  monthlyTransactionVolume?: number;
  contactPerson: string;
  contactEmail: string;
  status?: string;
}
