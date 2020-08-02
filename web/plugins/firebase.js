import * as firebase from 'firebase/app';
import 'firebase/auth';
import 'firebase/firestore';

const firebaseConfig = {
  apiKey: process.env.apiKey,
  authDomain: process.env.authDomain,
  databaseURL: process.env.databaseURL,
  projectId: process.env.projectId,
  storageBucket: process.env.storageBucket,
  messagingSenderId: process.env.messagingSenderId,
  appId: process.env.appId,
  measurementId: process.env.measurementId,
};

!firebase.apps.length ? firebase.initializeApp(firebaseConfig) : '';

const fireDb = firebase.firestore();

export {fireDb};

export const auth = firebase.auth();
// export const firebase
