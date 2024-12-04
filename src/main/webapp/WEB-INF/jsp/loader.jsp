 <div class="loader hidden"></div> 

<style>
.loader {
  width: 28px;
  aspect-ratio: 1;
  border-radius: 50%;
  background: #E3AAD6;
  transform-origin: top;
  display: grid;
  animation: l3-0 1s infinite linear;
}
.loader::before,
.loader::after {
  content: "";
  grid-area: 1/1;
  background:#F4DD51;
  border-radius: 50%;
  transform-origin: top;
  animation: inherit;
  animation-name: l3-1;
}
.loader::after {
  background: #F10C49;
  --s:180deg;
}
@keyframes l3-0 {
  0%,20% {transform: rotate(0)}
  100%   {transform: rotate(360deg)}
}
@keyframes l3-1 {
  50% {transform: rotate(var(--s,90deg))}
  100% {transform: rotate(0)}
}
</style> 