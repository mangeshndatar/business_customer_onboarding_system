import {
  ApplicationStatus,
  IndustryType,
  LegalStructure,
} from '../enums/legalstructure.enum';

export interface Application {
  id?: number;
  applicationId?: string;
  legalName: string;
  legalStructureType: LegalStructure;
  countryOfIncorporation: string;
  businessRegistrationNumber: string;
  taxIdentificationNumber: string;
  industryType: IndustryType;
  primaryContactPerson: string;
  contactEmail: string;
  estimatedAnnualTurnover: number;
  expectedMonthlyTransactionVolume: number;
  status?: ApplicationStatus;
  submissionDate?: Date;
  lastUpdated?: Date;
  directors: Director[];
  ultimateBeneficialOwners: UltimateBeneficialOwner[];
  documents: Document[];
  createdAt?: Date;
}
export interface Director {
  id?: number;
  name: string;
  nationalIdPassport: string;
  countryOfResidence: string;
}

export interface UltimateBeneficialOwner {
  id?: number;
  name: string;
  ownershipPercentage: number;
  countryOfResidence: string;
}

export interface Document {
  id?: number;
  fileName: string;
  filePath: string;
  fileType: string;
  fileSize: number;
  uploadDate?: Date;
}

export interface Response {
  content: Application[];
  empty: boolean;
  first: boolean;
  last: boolean;
  number: number;
  numberOfElements: number;
  pageable: {
    pageNumber: number;
    pageSize: number;
    sort: Sort;
    offset: number;
  };
  size: number;
  sort: Sort;
  totalElements: number;
  totalPages: number;
}

export interface Sort {
  empty: boolean;
  unsorted: boolean;
  sorted: boolean;
}
