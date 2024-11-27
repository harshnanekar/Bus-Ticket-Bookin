<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<aside class="sidebar">
  <div class="flex flex-col justify-center items-center py-4">
    <img
      class="w-1/2 mb-4"
      src="/resource/images/bus-canva-new.png"
      alt="Bus Logo"
    />
    <ul class="sidebar-items w-full flex flex-col justify-center items-center">
      <c:forEach var="module" items="${modules}">
        <li>
          <a class="text-lg flex items-center" href="${module.url}" data-sidebar="${module.url}"
            ><img
              src="/resource/images/${module.icon}.svg"
              style="width: 20px; height: 20px"
            />${module.modules}</a
          >
        </li>
      </c:forEach>
    </ul>
  </div>
</aside>
