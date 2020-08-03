export default function ({store, redirect}) {
  if (!store.state.user) {
    console.log(store.state.user);
    return redirect('/auth/login');
  }
}
